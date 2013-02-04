<%@ page import="gr.kstamatis.nlg.domain.Record" %>



<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'displayValue', 'error')} ">
	<label for="displayValue">
		<g:message code="record.displayValue.label" default="Display Value" />
		
	</label>
	<g:textField name="displayValue" value="${recordInstance?.displayValue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'isoValue', 'error')} ">
	<label for="isoValue">
		<g:message code="record.isoValue.label" default="Iso Value" />
		
	</label>
	<g:textField name="isoValue" value="${recordInstance?.isoValue}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'recordId', 'error')} ">
	<label for="recordId">
		<g:message code="record.recordId.label" default="Record Id" />
		
	</label>
	<g:textField name="recordId" value="${recordInstance?.recordId}"/>
</div>

