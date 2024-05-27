## Para gerar o jar do projeto
 ./gradlew fatJar
 
## para executar: 
java -jar build/libs/sincronizacao-0.0.1-SNAPSHOT.jar /tmp/input

## OBs 1 o primeiro argumeno "/tmp/input" é o folder de onde se quer ler os arquivos .CSV para ser processados
## OBs 2 os arquivos de saída com o resultado do processamento são gerados nesse mesmo folder com o nome terminado em "-outSync.csv"