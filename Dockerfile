FROM golang:1.14.0

# Setup
RUN mkdir /app
WORKDIR /app
ADD . /app

# Install app dependencies
RUN curl -sL https://deb.nodesource.com/setup_13.x | bash -
RUN curl -sL https://download.clojure.org/install/linux-install-1.10.1.536.sh | bash -
RUN apt install -y nodejs openjdk-11-jdk

# Build app dependencies
RUN npm install
RUN npm run cljs:build
RUN npm run sass:build

# Install app
RUN go get
RUN go build -o app

CMD ./app