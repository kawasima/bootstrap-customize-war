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
			<p>This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.</p>
			<p><a class="btn btn-primary btn-large">Learn more</a></p>
		</div>

		<div class="row">
		<p>Web design encompasses many different skills and disciplines in the production and maintenance of websites.[1] The different areas of web design include web graphic design; interface design; authoring, including standardised code and proprietary software; user experience design; and search engine optimization.
		Often many individuals will work in teams covering different aspects of the design process, although some designers will cover them all.[2] The term web design is normally used to describe the design process relating to the front-end (client side) design of a website including writing mark up, but this is a grey area as this is also covered by web development. Web designers are expected to have an awareness of usability and if their role involves creating mark up then they are also expected to be up to date with web accessibility guidelines.</p>
		</div>
	</div>
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