{{- define "auth-chart.name" -}}
authservice
{{- end }}

{{- define "auth-chart.fullname" -}}
{{ include "auth-chart.name" . }}-{{ .Release.Name }}
{{- end }}
