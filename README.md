unimarc-authority-viewer
========================

Introduction
------------
Unimarc Authority Viewer is a web application to display Unimarc (or UnimarcXML) authority records from a file.

Firstly, a lucene script creates the search index.

Then, Unimarc records are inserted into the database (in Bootstrap init method).

The user is able to browse or search the authority file.

A live demo of thie web app can be seen <a target="_blank" href="http://nlg.kstamatis.gr/nlg">here</a>, which displays the authority file recently published by the <a target="_blank" href="http://www.nlg.gr">National Library of Greece</a>.

Files
------------
Along with the project, you can find the following files/folders in it

1) <i>/web-app/resource/records.xml</i> - The records that are used in the aforementioned demo

2) <i>/web-app/resource/index</i> - A folder containing the lucene index of the previous records (You can create a new index using the <i>Index.java</i> class under the path: <i>src/java/gr/kstamatis/nlg/</i>

Technologies
------------
The web application has been developed using the <a targer="_blank" href="http://grails.org/">Grails</a> framework.
Moreover, the following libraries have been used:
<ul>
<li><a targer="_blank" href="http://marc4j.tigris.org/">Marc4j</a></li>
<li><a targer="_blank" href="http://marc4j.tigris.org/servlets/ProjectDocumentList?folderID=7638&expandFolder=7638&folderID=0"> Marc4j-lucene</a></li>
<li><a targer="_blank" href="http://lucene.apache.org/core/">Lucene</a></li>
</ul>

Author
------------
<b><a target="_blank" href="http://about.me/kstamatis">Kostas Stamatis</a></b><br/>
<a target="_blank" href="http://www.ekt.gr/">National Documentation Center</a> / <a target="_blank" href="http://www.eie.gr/">NHRF</a>
