Handlebars.TemplateLoader.config({
	prefix: "/hbs/"
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
	}
});

var DribbleShotsView = Backbone.View.extend({
	initialize: function() {
		this.collection = new DribbbleShots();
		this.collection.bind('reset', this.render);
		this.collection.fetch();
	},
	render: function() {
		
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
		}
		this.$el.modal('show');
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
