FROM postgres:15.1
ADD scripts/dbinit.sh /docker-entrypoint-initdb.d
RUN chmod a+r /docker-entrypoint-initdb.d/*
EXPOSE 5432