./mvnw clean package
mkdir target/extracted
java -Djarmode=layertools -jar target/music-player.jar extract --destination target/extracted
docker build -t music .
