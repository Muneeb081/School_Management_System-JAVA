FROM postgres:14.5-alpine
USER postgres
RUN whoami
COPY ./scripts/init.sql /docker-entrypoint-initdb.d/init.sql
# RUN chown postgres:postgres /docker-entrypoint-initdb.d/init.sql
ENTRYPOINT ["docker-entrypoint.sh"]
EXPOSE 5432
CMD ["postgres"]