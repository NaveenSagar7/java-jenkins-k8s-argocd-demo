# Architecture -- Jenkins + ArgoCD GitOps CI/CD

This project demonstrates a GitOps-based CI/CD pipeline where Jenkins
performs the CI stages (build, analysis, containerization) and ArgoCD
handles the CD stage (deployment to Kubernetes).

## High-Level Flow

Developer Push │ ▼ GitHub (Application Repository) │ ▼ Jenkins Pipeline
│ ├── Checkout Source Code ├── Maven Build ├── SonarQube Code Analysis
├── Quality Gate Validation │ ├── Docker Image Build ├── Docker Image
Push (DockerHub) │ └── Update Kubernetes Manifest Repository │ ▼ GitHub
(Manifest Repository) │ ▼ ArgoCD │ ▼ Kubernetes Cluster

## Key Idea

Jenkins does **not deploy directly to Kubernetes**.

Instead:

1.  Jenkins builds and pushes the container image.
2.  Jenkins updates the Kubernetes manifest repository.
3.  ArgoCD continuously watches the manifest repository.
4.  When changes are detected, ArgoCD synchronizes the cluster
    automatically.

This approach is called **GitOps**, where Git acts as the single source
of truth for deployments.
