Handlebars.TemplateLoader.config({
	prefix: "/hbs/"
});

var DribbbleColor  = Backbone.Model.extend({
});

var DribbbleColors = Backbone.Collection.extend({
	url: "/dribbble/colors",
	model: DribbbleColor
});
var DribbbleShot = Backbone.Model.extend({
});

var DribbbleShots = Backbone.Collection.extend({
	model: DribbbleShot,
	url: "http://api.dribbble.com/shots/popular",
	sync: function(method, model, options) {
		options.timeout = 10000;
		options.dataType = "jsonp";
		return Backbone.sync(method, model, options);
	},
	parse: function(response) {
		return response.shots;
	}
});

var DribbbleColorsView = Backbone.View.extend({
	el: "#dribbble-colors",
	initialize: function(options) {
		this.collection = new DribbbleColors();
		this.collection.bind('reset', this.render, this);
		this.collection.fetch({data: {url: options.url}});
	},
	render: function() {
		this.$el.html(Handlebars.TemplateLoader.merge("dribbble-colors", this.collection.toJSON()));
	}
});

var DribbbleShotsView = Backbone.View.extend({
	events: {
		"click a.dribbble-shot": "showColors"
	},
	el: "#dribbble-shots .carousel",
	initialize: function() {
		this.collection = new DribbbleShots();
		this.collection.bind('reset', this.render, this);
		this.collection.fetch();
	},
	render: function() {
		$(".carousel-inner", this.$el).html(
				Handlebars.TemplateLoader.merge("dribbble-shots", this.collection.toJSON()));
		this.$el.carousel({interval: false});
		return this;
	},
	showColors: function(event) {
		var url = $(event.target).parent().attr("data-url");
		var dribbbleColorsView = new DribbbleColorsView({url: url});
	}
});

var MainDialogView = Backbone.View.extend({
	el: "#customize-this",
	initialize: function() {
		this.render();
	},
	render: function() {
		if (this.$el.size() == 0) {
			var dialog = $('<div id="customize-this">aaa</div>').appendTo($("body"));
			this.setElement(dialog);
			this.$el.html(Handlebars.TemplateLoader.merge("dialog"));
			this.dribbbleShotsView = new DribbbleShotsView();
		}
		return this;
	}
});

var CustomizeBootstrap = Backbone.Router.extend({
	routes: {
		"": "index"
	},
	index: function() {
		new MainDialogView();
	}
});
