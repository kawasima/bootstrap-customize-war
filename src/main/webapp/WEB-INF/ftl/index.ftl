<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta property="bootstrap:baseColor" content="${baseColor}"/>
	<meta property="bootstrap:mainColor" content="${mainColor}"/>
	<meta property="bootstrap:accentColor" content="${accentColor}"/>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css.action"/>
	<link rel="stylesheet" type="text/css" href="/css/style.css"/>
	<script src="/js/jquery-1.8.3.min.js"></script>
	<script src="/js/jquery-ui-1.9.2.min.js"></script>
	<script src="/js/underscore-1.4.2.min.js"></script>
	<script src="/js/backbone-0.9.2.min.js"></script>
	<script src="/js/jquery.carouFredSel-6.1.0.min.js"></script>
	<script src="/js/handlebars-1.0.rc1.js"></script>
	<script src="/js/handlebars-loader-0.1.0.js"></script>
	<script src="/js/app.js"></script>
</head>
<body>
<#include examplePath parse=false>
		<script>
Handlebars.TemplateLoader.load(["dialog", "dribbble-shots", "dribbble-colors"], {
	complete: function() {
		var router = new CustomizeBootstrap();
		Backbone.history.start();
	}
});
	</script>

</body>
</html>