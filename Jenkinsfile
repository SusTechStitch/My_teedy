pipeline {
    agent any

    parameters {
        string(
            name: 'DOCKER_IMAGE',
            defaultValue: 'jiang040301/teedy-app',
            description: 'Docker Hub image name, for example: username/teedy-app'
        )
        string(
            name: 'DOCKER_HUB_CREDENTIALS',
            defaultValue: 'dockerhub_credentials',
            description: 'Jenkins credentials ID for Docker Hub'
        )
    }

    environment {
        JAVA_HOME = "/Users/jianggg/Library/Java/JavaVirtualMachines/ms-17.0.16/Contents/Home"
        PATH = "/Users/jianggg/Library/Java/JavaVirtualMachines/ms-17.0.16/Contents/Home/bin:/opt/homebrew/bin:/opt/homebrew/opt/maven/bin:/usr/local/bin:/Applications/Docker.app/Contents/Resources/bin:${env.PATH}"
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
                sh 'docker --version'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn -B -DskipTests clean package'
                sh 'ls -l docs-web/target/docs-web-*.war'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${params.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: params.DOCKER_HUB_CREDENTIALS,
                    usernameVariable: 'DOCKER_HUB_USERNAME',
                    passwordVariable: 'DOCKER_HUB_TOKEN'
                )]) {
                    sh 'echo "$DOCKER_HUB_TOKEN" | docker login -u "$DOCKER_HUB_USERNAME" --password-stdin'
                    sh "docker push ${params.DOCKER_IMAGE}:${env.DOCKER_TAG}"
                    sh "docker tag ${params.DOCKER_IMAGE}:${env.DOCKER_TAG} ${params.DOCKER_IMAGE}:latest"
                    sh "docker push ${params.DOCKER_IMAGE}:latest"
                }
            }
        }

        stage('Run Three Containers') {
            steps {
                script {
                    ['8082', '8083', '8084'].each { port ->
                        sh "docker rm -f teedy-container-${port} || true"
                        sh "docker run -d --name teedy-container-${port} -p ${port}:8080 ${params.DOCKER_IMAGE}:${env.DOCKER_TAG}"
                    }
                }
                sh 'docker ps --filter "name=teedy-container" --format "table {{.Names}}\\t{{.Image}}\\t{{.Ports}}\\t{{.Status}}"'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true, allowEmptyArchive: true
            junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true
        }
    }
}
