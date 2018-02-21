FROM registry.access.redhat.com/jboss-eap-6/eap-openshift:6.4

USER root:root
RUN chmod -R 777 /opt/eap/standalone/
USER wildfly

COPY maven/ /opt/eap/standalone/deployments/
