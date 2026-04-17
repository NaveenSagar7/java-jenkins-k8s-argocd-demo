pipeline {

    agent any

    environment {
        DOCKERHUB_USER = "naveen352"
        DOCKER_IMAGE = "${DOCKERHUB_USER}/java-demo-argocd"
        VERSION = "v${BUILD_NUMBER}"

    }

    stages {
        /*
        stage('Checkout') {
            steps {
                git 'https://github.com/username/java-jenkins-k8s-argocd-demo.git'
            }
        }
        */

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                    waitForQualityGate abortPipeline: true
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build --no-cache -t $DOCKER_IMAGE:$VERSION ."
            }
        }

        stage('Docker Push')  {
            steps {
                withDockerRegistry([credentialsId: 'dockerhub-cred', url: '']) {
                    sh "docker push $DOCKER_IMAGE:$VERSION"
                }
            }
        }

        stage('Update & Push Manifest') {
            steps {
                dir('java-k8s-manifests') {
                    withCredentials([usernamePassword(
                        credentialsId: 'k8s-manifest-token',
                        usernameVariable: 'GIT_USER',
                        passwordVariable: 'GIT_TOKEN'
                    )]) {
                        sh '''
                        chmod +x scripts/update_image.sh
                        ./scripts/update_image.sh $VERSION
                        git remote -v
                        git remote set-url origin https://$GIT_USER:$GIT_TOKEN@github.com/NaveenSagar7/java-k8s-manifests.git
                        git push origin main
                        '''
                    }
                }
            }
        }

    }

}
