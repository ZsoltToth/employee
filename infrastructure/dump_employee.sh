#!/usr/bin/env bash

#docker run --volume $(pwd)/sql:/home/mysql  mysql:8 /bin/bash -c "ls /home/mysql"

if [[ ! -f sql/employee-dump.sql ]]
then
  echo "Dump employee database"
  docker run --volume $(pwd)/sql:/home/mysql:rw  mariadb:10.6 mysqldump employee \
  --result-file=/home/mysql/employee-dump.sql \
  --user=guest \
  --host=relational.fit.cvut.cz \
  --port=3306 \
  --password=relational
fi

#Remove ROW_FORMAT=FIXED engine options
#https://magento.stackexchange.com/questions/94325/how-to-fix-mysql-error-1031-table-storage-engine-for-catalog-product-relation
sed  --regexp-extended 's/ROW_FORMAT=FIXED//g' sql/employee-dump.sql > sql/employee.sql