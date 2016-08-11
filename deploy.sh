git pull
gradle clean build fatJar
cp kafka-connect-slack/build/libs/kafka-connect-slack-0.1-3.0.0-all.jar /opt/dm-stream-reactor/kafka-connect-slack2/
systemctl daemon-reload
service confluent-kafka-connect restart &
tail -f  /var/log/ansible-confluent/connect.log