<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css.action"/>
	<script src="/js/jquery-1.8.3.min.js"></script>
	<script src="/js/underscore-1.4.2.min.js"></script>
	<script src="/js/backbone-0.9.2.min.js"></script>
	<script src="/js/bootstrap-modal.js"></script>
	<script src="/js/handlebars-1.0.rc1.js"></script>
	<script src="/js/handlebars-loader-0.1.0.js"></script>
	<script src="/js/machida.js"></script>
</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="brand" href="./">Customized Bootstrap</a>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="hero-unit">
			<h1>Customize Bootstrap</h1>
		</div>

		<div class="row">
		おおおお
		</div>
	</div>
		<script>
Handlebars.TemplateLoader.load(["dialog"], {
	complete: function() {
		var router = new CustomizeBootstrap();
		Backbone.history.start();
	}
});
	</script>

</body>
</html>