pipeline {

    agent any

    environment {

        DOCKER_IMAGE = "dockerhub-user/java-demo"
        VERSION = "v${BUILD_NUMBER}"

    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/username/java-jenkins-k8s-argocd-demo.git'
            }
        }

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
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t $DOCKER_IMAGE:$VERSION ."
            }
        }

        stage('Docker Push') {
            steps {
                sh "docker push $DOCKER_IMAGE:$VERSION"
            }
        }

        stage('Update Manifest Repo') {
            steps {
        
                // ---------- SCENARIO 1 : PUBLIC MANIFEST REPOSITORY ----------
                // If the Kubernetes manifest repository is public,
                // Jenkins can clone and push directly (if push permission exists).
        
                sh '''
                git clone https://github.com/username/java-k8s-manifests.git
                cd java-k8s-manifests
                ../scripts/update-image.sh ${VERSION}
                '''
        
                /*
                ---------- SCENARIO 2 : PRIVATE MANIFEST REPOSITORY ----------
                If the manifest repository is private, Jenkins must use credentials.
                Store a GitHub Personal Access Token in Jenkins Credentials Manager.
        
                Manage Jenkins → Credentials → Add Credentials
                Type: Username + Password
                ID: github-token
        
                Then use the credentials like this:
        
                withCredentials([usernamePassword(
                    credentialsId: 'github-token',
                    usernameVariable: 'GIT_USER',
                    passwordVariable: 'GIT_TOKEN'
                )]) {
        
                    sh '''
                    git clone https://${GIT_USER}:${GIT_TOKEN}@github.com/username/java-k8s-manifests.git
                    cd java-k8s-manifests
                    ../scripts/update-image.sh ${VERSION}
                    '''
                }
                */
        
            }
        }

    }

}
