FROM gradle:latest

#GRADLE_HOME

ARG USER_ID=1001
ARG GROUP_ID=1001

RUN groupadd -g $GROUP_ID user && \
    useradd -u $USER_ID -s /bin/sh -m -d /home/user -g user user && \
    chgrp -R  user $GRADLE_HOME && \
    chmod -R g+w $GRADLE_HOME

