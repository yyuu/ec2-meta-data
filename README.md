# ec2-meta-data

## Overview

A utility for jetty to obtain meta-data of EC2 instance.


## Build

Just use maven.

    % mvn clan package

Now you will get jar in ./target.


## Configuration

First of all, place built jar into ${JETTY_HOME}/lib/ext.

Configuring instance meta-data in ${JETTY_HOME}/etc/jetty.xml.

    <?xml version="1.0"?>
    <Configure id="Server" class="org.eclipse.jetty.server.Server">

      (... snip ...)

      <Call name="instanceId" class="com.geishatokyo.jetty.ec2.AmazonEC2MetaData" name="get">
        <Arg>/latest/meta-data/instance-id</Arg> <!-- instance-id of instance -->
        <Arg>xxx</Arg> <!-- default value on failures -->
      </Call>
      <Get name="sessionIdManager">
        <Set name="workerName"><Ref id="instanceId" /></Set>
      </Set>
    <!--
      Server server = new Server();
      String instanceId = com.geishatokyo.jetty.ec2.AmazonEC2MetaData.get("/latest/meta-data/instance-id", "xxx");
      idManager.server.getSessionIdManager()
      idManager.getWorkerName(instanceId);
      -->
    </Configure>


## License

Copyright (c) 2011 Geisha Tokyo Entertainment, Inc.

All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
and Apache License v2.0 which accompanies this distribution.

The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html

The Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php

You may elect to redistribute this code under either of these licenses.


## Author

Copyright (C) 2011 Geisha Tokyo Entertainment, Inc.

Yamashita, Yuu <yamashita@geishatokyo.com>
