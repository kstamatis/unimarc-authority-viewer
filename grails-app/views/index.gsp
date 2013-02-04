<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Αρχείο Επικεφαλίδων ΕΒΕ: Αρχική</title>
		<style type="text/css" media="screen">
			#status {
				background-color: #eee;
				border: .2em solid #fff;
				margin: 2em 2em 1em;
				padding: 1em;
				width: 12em;
				float: left;
				-moz-box-shadow: 0px 0px 1.25em #ccc;
				-webkit-box-shadow: 0px 0px 1.25em #ccc;
				box-shadow: 0px 0px 1.25em #ccc;
				-moz-border-radius: 0.6em;
				-webkit-border-radius: 0.6em;
				border-radius: 0.6em;
			}

			.ie6 #status {
				display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
			}

			#status ul {
				font-size: 0.9em;
				list-style-type: none;
				margin-bottom: 0.6em;
				padding: 0;
			}

			#status h1 {
				text-transform: uppercase;
				font-size: 1.1em;
				margin: 0 0 0.3em;
			}

			#page-body {
				margin: 2em 1em 1.25em 3em;
			}

			h2 {
				margin-top: 1em;
				margin-bottom: 0.3em;
				font-size: 1em;
			}

			p {
				margin: 0.25em 0;
			}

			#controller-list ul {
				list-style-position: inside;
			}

			#controller-list li {
				list-style-position: inside;
				margin: 0.25em 0;
			}

			@media screen and (max-width: 480px) {
				#status {
					display: none;
				}

				#page-body {
					margin: 0 1em 1em;
				}

				#page-body h1 {
					margin-top: 0;
				}
			}
		</style>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="page-body" role="main">
			<h1>Καλώς ήλθατε</h1>
			<p>Σύμφωνα με την πολιτική της παρούσας Διοίκησης για ουσιαστική ανέλιξη των υπηρεσιών της <a target="_blank" href="http://www.nlg.gr/">Εθνικής Βιβλιοθήκης της Ελλάδος (ΕΒΕ)</a> και ελεύθερη πρόσβαση του κοινού σε αυτές και με βάση το θεσμικό της ρόλο ως το εθνικό βιβλιογραφικό κέντρο της Ελλάδος, η ΕΒΕ παρέχει το Αρχείο Καθιερωμένων Επικεφαλίδων της σε μορφή ISO2709.</p>
			<br/><p>Στην ιστοσελίδα αυτή μπορεί κανείς να προσπελάσει το αρχείο αυτό των επικεφαλίδων είτε κάνοντας πλοήγηση είτε με αναζήτηση.</p>
			
			<br/><p>Στο αρχείο περιλαμβάνονται οι ακόλουθες κατηγορίες καθιερωμένων επικεφαλίδων:</p><br/>
			<ul style="padding-left:30px">
				<li><g:link controller="record" action="list" params="[tag:'200']">Ονόματα Φυσικών Προσώπων</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'210']">Συλλογικά Όργανα</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'250']">Θεματικές Επικεφαλίδες</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'215']">Γεωγραφικά Ονόματα</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'230']">Ομοιόμορφοι Τίτλοι</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'220']">Οικογενειακά Ονόματα</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'240']">Όνομα / Τίτλος ως Θέμα</g:link></li>
				<li><g:link controller="record" action="list" params="[tag:'225']">Σειρές</g:link></li>
			</ul>
			<br/>
			<div id="controller-list" role="navigation">
				<h2>Πλοήγηση</h2><br/>
				<g:link controller="record" action="list">πλοήγηση σε όλες τις θεματικές κατηγορίες</g:link><br/>
				<br/><br/>
				<h2>Αναζήτηση</h2><br/>
				<g:form controller="search" action="search">
					<g:select name="searchtype" from="['Όλες οι κατηγορίες','Ονόματα Φυσικών Προσώπων', 'Συλλογικά Όργανα', 
					'Θεματικές Επικεφαλίδες', 'Γεωγραφικά Ονόματα', 'Ομοιόμορφοι Τίτλοι', 'Οικογενειακά Ονόματα',
					'Όνομα / Τίτλος ως Θέμα', 'Σειρές']" keys="['-1', '200', '210', '250', '215', '230', '220', '240', '225']"></g:select>
					<g:textField name="query"/>
					<g:submitButton name="submit" value="Go!"/>
				</g:form>
			</div>
		</div>
	</body>
</html>
