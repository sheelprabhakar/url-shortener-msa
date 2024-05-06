# url-shortener-msa
A sample microsevice based URL shortener service project to showcase how to do microservices development. This 
repository consist spring based 3 services with basic integration tests, GitHub action based end to end build and 
k8s deployment pipeline.

**Note: This project is to only showcase MSA for URL shortening service using GHA pipeline on K8S, Window/MAC system using Docker desktop.
This repo has very minimal functionality.**

## Prerequisites
1. JDK 21
1. Docker Desktop with WSL2
1. K8S enabled in docker desktop
1. 32 GB RAM
2. Little knowledge of K8s and GitHub actions runner
3. For better understanding I will suggest to use IntelliJ Idea

## Gettings started
To Build and run without GHA pipeline, just clone and run build and deploy project using setup.bat file.
First make sure docker desktop(WSL2) is up and running with K8s enabled. [Docker Desktop](https://www.docker.com/blog/how-kubernetes-works-under-the-hood-with-docker-desktop/)
Login to Docker desktop using your Docker hub account. If you don't have docker hub account then create it for free and login. It is important to build and push images in you docker repository
Run and test kubectl command if it is working fine.

### Building and testing using IntelliJ
Using IntelliJ Idea create new project from version control and cloning this repository and Build project using gradle.
For development, you can run all required services in docker by running 

```
url-shortener-msa\docker> docker-compose up 
```
Then you can run all 3 XxxxServiceApplication.java file using intelliJ

## Deploying Pre Build Docker Images from my Docker Hub 
```
url-shortener-msa> reset-deployment.bat
```
This will deploy all backend, services and Nginx Ingress proxy.
Latest version of the Docker Desktop provided "kubernetes.docker.internal" internal domain. You test services using following GET requests
```
GET http://kubernetes.docker.internal/shortener/api/v1/?url=https://stackoverflow.com/questions/56726429/how-to-run-multiple-commands-in-one-github-actions-docker
```
This will return shorten URL like http://kubernetes.docker.internal/Pm00000

Open this URL and this will redirect you to actual stackoverflow URL.

## Building and Deploying directly
Clone repository
Create 3 public artifactory(you can try private if you can create credential in k8s and use that in your deployment file) in https://hub.docker.com/ named token-service, shortener-service, redirect-service 
Change Container Image path from sheelprabhakar/ to your docker hub repository in the following files
1. token-service.yaml
2. shortener-service.yaml
3. redirect-service.yaml

Change Dockerhub artifactory name in setup.bat file for docker push command.
Execute setup.bat file, ideally it should compile, test, build and push 3 services and deploy to your local K8s.

## Setup CI/CD Pipeline using GHA
Written 5 GHA workflow under .github/workflows, on your windows(wls install docker demon)/Mac and setup self-hosted GHA runner in your forked repository.
Follow [change to your repository](https://github.com/sheelprabhakar/url-shortener-msa/settings/actions/runners/new) 
and follow instructions on page to install and setup self Hosted GHA runner.
checkout to branch named dev
Fork repository to you github account, Make all changes given in the above "Building and Deploying directly" section.
Push your code GHA will execute work flow to build, test and deploy code 