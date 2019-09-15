node {
    stage("Pull Repo"){
        properties([parameters([string(defaultValue: '54.89.213.223', description: '''Dev:54.89.213.223 QA:34.207.157.146 Prod:54.167.194.28''', name: 'Remote_instance', trim: false)])])
        git 'https://github.com/leventelibal/jenkins_repo.git'
    }
    stage("Install Apache"){
        sh "ssh   centos@${Remote_instance}    sudo yum install httpd -y"
    }
    stage("Create Index.html"){
        sh "scp  index.html  centos@${Remote_instance}:/tmp"
    }
    stage("Move Files"){
        sh "ssh   centos@${Remote_instance}    sudo mv /tmp/index.html  /var/www/html/index.html"
    }
    stage("Restart httpd"){
        sh "ssh   centos@${Remote_instance} sudo systemctl restart  httpd"
    }
}