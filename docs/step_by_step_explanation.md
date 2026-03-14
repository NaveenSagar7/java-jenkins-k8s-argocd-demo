# Step‑by‑Step Pipeline Explanation

This section explains what happens from the moment a developer pushes
code until the application is deployed in Kubernetes.

------------------------------------------------------------------------

## 1. Developer Push

A developer pushes application code to the GitHub repository:

git push origin main

The repository contains:

-   Java source code
-   Dockerfile
-   Jenkinsfile
-   CI pipeline scripts

------------------------------------------------------------------------

## 2. GitHub Webhook

GitHub sends a webhook event to Jenkins whenever a commit is pushed.

This triggers the Jenkins pipeline automatically.

------------------------------------------------------------------------

## 3. Jenkins Pipeline Starts

Jenkins reads the **Jenkinsfile** from the repository and begins
executing the defined stages.

------------------------------------------------------------------------

## 4. Checkout Stage

Jenkins clones the repository into its workspace.

git clone https://github.com/username/java-jenkins-k8s-argocd-demo.git

This ensures Jenkins builds the latest version of the code.

------------------------------------------------------------------------

## 5. Maven Build

Jenkins compiles and packages the Java application.

mvn clean package

Output artifact:

target/java-demo.jar

------------------------------------------------------------------------

## 6. SonarQube Analysis

Jenkins runs static code analysis using SonarQube.

mvn sonar:sonar

SonarQube scans the code for:

-   Bugs
-   Code smells
-   Security vulnerabilities
-   Code duplication
-   Code coverage

Results appear in the SonarQube dashboard.

------------------------------------------------------------------------

## 7. Quality Gate Validation

Jenkins waits for SonarQube to evaluate the code against predefined
rules.

waitForQualityGate abortPipeline: true

If the quality gate fails, the pipeline stops and deployment is
prevented.

------------------------------------------------------------------------

## 8. Docker Image Build

Jenkins builds a Docker image for the application.

docker build -t dockerhub-user/java-demo:v\${BUILD_NUMBER} .

Each build is versioned using the Jenkins build number.

------------------------------------------------------------------------

## 9. Docker Image Push

The image is pushed to DockerHub.

docker push dockerhub-user/java-demo:v\${BUILD_NUMBER}

The Kubernetes cluster will later pull this image.

------------------------------------------------------------------------

## 10. Update Manifest Repository

Instead of deploying directly to Kubernetes, Jenkins updates the
Kubernetes manifest repository.

Example change in deployment.yaml:

image: dockerhub-user/java-demo:v27

This ensures the cluster deploys the new image version.

------------------------------------------------------------------------

## 11. Commit and Push Manifest Changes

Jenkins commits and pushes the updated manifest.

git commit -m "update image version v27 \[skip ci\]" git push

------------------------------------------------------------------------

## 12. ArgoCD Detects the Change

ArgoCD continuously watches the manifest repository.

When a new commit appears, ArgoCD automatically synchronizes the
cluster.

------------------------------------------------------------------------

## 13. Kubernetes Deployment

ArgoCD applies the updated manifest to the Kubernetes cluster.

Kubernetes performs a rolling update:

Old pods → terminated\
New pods → started with the updated image

------------------------------------------------------------------------

## Final Result

The application is automatically deployed with the latest container
image built by Jenkins.
