# Functions
Meu repositório maven.

 <server>
      <id>private-repo</id>
	   <configuration>
        <httpHeaders>
          <property>
            <name>Authorization</name>
            <!-- Base64-encoded username:access_token -->
            <value>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</value>
          </property>
        </httpHeaders>
      </configuration>
    </server>

<repositories>
		<repository>
					  <id>private-repo</id>
					  <url>{Place the RAW github url here}</url>
					  <releases>
						  <enabled>true</enabled>
						  <updatePolicy>never</updatePolicy>
					  </releases>
					  <snapshots>
						  <enabled>false</enabled>
					  </snapshots>
		 </repository>
	</repositories>
