set projectLocation=/d "D:\workspace\automatedscripts"

cd %projectLocation%

mvn clean test -DsuiteXmlFile=testng.xml