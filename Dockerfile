FROM golang:1.14.0

# Setup
RUN mkdir /app
WORKDIR /app
ADD . /app

# Install
RUN go get
RUN go build

CMD app