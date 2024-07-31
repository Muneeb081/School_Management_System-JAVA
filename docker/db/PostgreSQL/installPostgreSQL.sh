docker network create --driver bridge docker_alabtaalnet || true
docker build -t postgres .
 docker run -p 5432:5432 \
            -d \
            -u root \
            --name postgres \
            --net=docker_alabtaalnet \
            --restart always \
            -e POSTGRES_PASSWORD=Zahid123 \
            postgres