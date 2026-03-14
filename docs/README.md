# java-jenkins-k8s-argocd-demo

This repository demonstrates a **GitOps-based CI/CD pipeline** for
deploying a Java application to Kubernetes using:

-   Jenkins
-   Maven
-   SonarQube
-   Docker
-   ArgoCD
-   Kubernetes

Instead of Jenkins deploying directly to Kubernetes, Jenkins updates a
separate **manifest repository**. ArgoCD watches that repository and
automatically deploys changes to the cluster.

------------------------------------------------------------------------

# Architecture

The pipeline follows this architecture:

Developer → GitHub → Jenkins → Docker Registry → Manifest Repo → ArgoCD
→ Kubernetes

See `docs/architecture.md` for a detailed explanation.

------------------------------------------------------------------------

# Pipeline Stages

The Jenkins pipeline performs the following steps:

1.  Checkout source code from GitHub
2.  Build the Java application using Maven
3.  Run SonarQube static code analysis
4.  Validate quality gate rules
5.  Build Docker image
6.  Push Docker image to DockerHub
7.  Update Kubernetes manifest repository
8.  ArgoCD detects the change and deploys automatically

------------------------------------------------------------------------

# Repositories Used

This setup uses **two repositories**.

## 1. Application Repository

java-jenkins-k8s-argocd-demo

Contains:

-   Java source code
-   Dockerfile
-   Jenkinsfile
-   CI scripts

## 2. Manifest Repository

java-k8s-manifests

Contains Kubernetes manifests such as:

-   deployment.yaml
-   service.yaml

ArgoCD continuously monitors this repository.

------------------------------------------------------------------------

# Prerequisites

To run this project you need:

-   Jenkins
-   Java 17
-   Maven
-   Docker
-   Kubernetes cluster (Minikube, Kind, EKS, etc)
-   kubectl
-   SonarQube
-   ArgoCD
-   DockerHub account

------------------------------------------------------------------------

# Running the Pipeline

Push code to GitHub:

git push origin main

This triggers the Jenkins pipeline automatically.

Once the pipeline finishes:

1.  Docker image is built and pushed
2.  Manifest repository is updated
3.  ArgoCD detects the change
4.  Kubernetes deploys the application

------------------------------------------------------------------------

# Expected Result

After successful deployment:

-   Pods are running in Kubernetes
-   ArgoCD shows the application as **Synced and Healthy**
