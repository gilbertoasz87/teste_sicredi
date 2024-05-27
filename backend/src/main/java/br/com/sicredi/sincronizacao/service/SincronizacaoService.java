package br.com.sicredi.sincronizacao.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sicredi.sincronizacao.dto.ContaDTO;
import br.com.sicredi.sincronizacao.timer.MeasuredExecutionTime;
import br.com.sicredi.sincronizacao.util.CsvGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SincronizacaoService {

	private static final String COMMA = ",";
	private static Logger log = LoggerFactory.getLogger(SincronizacaoService.class);

	@Autowired
	private BancoCentralService bacenService;

	@Autowired
	private CsvGeneratorUtil csvUtil;

	@MeasuredExecutionTime
	public void syncAccounts(String path) throws IOException {

		final File folder = new File(path);
		syncFolder(folder);

	}

	public void syncFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				syncFolder(fileEntry);
			} else if (fileEntry.getName().endsWith(".csv")){
				System.out.println(fileEntry.getPath());
				List<ContaDTO> contasIn = readCSV(fileEntry.getPath());
				List<ContaDTO> contasOut = new ArrayList<>();
				contasIn.stream().forEach(conta -> contasOut.add(conta.withStatus(bacenService.atualizaConta(conta))));
				
				String fileNameWithOutExt = fileEntry.getPath().replaceFirst("[.][^.]+$", "");
				writeCSV(contasOut, fileNameWithOutExt + "-outSync.csv");
			}
		}
	}

	private List<ContaDTO> readCSV(String inputFilePath) {
		List<ContaDTO> inputList = new ArrayList<ContaDTO>();
		try {
			File inputF = new File(inputFilePath);
			InputStream inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
			inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
			br.close();
		} catch (IOException e) {
			log.error("Erro ao ler arquivo");
		}

		return inputList;
	}

	private void writeCSV(List<ContaDTO> contas, String fileName) throws IOException {
		FileWriter writer = new FileWriter(fileName);
		writer.write(csvUtil.generateCsv(contas));
		writer.close();
	}

	private Function<String, ContaDTO> mapToItem = (line) -> {
		String[] p = line.split(COMMA);
		return new ContaDTO(p[0], p[1], Double.valueOf(p[2]), false);
	};

}
