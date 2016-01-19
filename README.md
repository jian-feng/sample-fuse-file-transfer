# sample-fuse-file-transfer
Demonstrate how to use JBoss Fuse as file transfer hub

To build this project use

    mvn install

To setup the sftp connection
	
	If your public/private rsa key pair already exists in ~/.ssh/id_rsa, you should skip this step
	ssh-keygen -t rsa
	
	Transfer you public rsa key to sftp Server
	ssh-copy-id -i .ssh/id_rsa.pub test@localhost

To run the project you can execute the following Maven goal

    mvn camel:run

To deploy the project in OSGi. For example using JBoss Fuse
or Apache Karaf. You can run the following command from its shell:

    osgi:install -s file:/tmp/filetransfer-0.0.1-SNAPSHOT.jar
    osgi:install -s mvn:com.sample/filetransfer/0.0.1-SNAPSHOT


After that, prepare test data on sftp server like below to kick start
	
	ssh test@localhost
	mkdir -p inDir/data
	mkdir -p inDir/trigger
	echo test1 > inDir/data/file1.dat
	echo test2 > inDir/data/file2.dat
	echo `date '+%Y%m%d'` > inDir/trigger/test.trg

Confirm the result
	
	ssh test@localhost
	tree -a inDir/
	tree -a outDir/

	if any error occured, the original files will be moved to inDir/data/.error

For more help see the Apache Camel documentation

    http://www.jboss.org/products/fuse/overview/
