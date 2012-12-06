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
		$("li.color", this.$el).draggable({helper: 'clone'});
	}
});

var DribbbleShotsView = Backbone.View.extend({
	events: {
		"click a.dribbble-shot": "showColors"
	},
	el: "#dribbble-shots-carousel",
	initialize: function() {
		this.collection = new DribbbleShots();
		this.collection.bind('reset', this.render, this);
		this.collection.fetch();
	},
	render: function() {
		this.$el.html(
				Handlebars.TemplateLoader.merge("dribbble-shots", this.collection.toJSON()));
		this.$el.carouFredSel({
			circular: true,
			scroll: { items: 1, fx: "none" },
			width: "100%",
			height: 150,
			responsive: true,
			auto: { play: false },
			prev: { button: "#shots-prev", key: "left" },
			next: { button: "#shots-next", key: "right" }
		});
		return this;
	},
	showColors: function(event) {
		var url = $(event.target).parent().attr("data-url");
		var dribbbleColorsView = new DribbbleColorsView({url: url});
	}
});

var MainDialogView = Backbone.View.extend({
	events: {
		"click #apply-color": "applyColors"
	},
	el: "#customize-this",
	initialize: function() {
		this.render();
	},
	render: function() {
		if (this.$el.size() == 0) {
			var dialog = $(Handlebars.TemplateLoader.merge("dialog", {
				baseColor:   $('head meta[property=bootstrap\\:baseColor]').attr("content"),
				mainColor:   $('head meta[property=bootstrap\\:mainColor]').attr("content"),
				accentColor: $('head meta[property=bootstrap\\:accentColor]').attr("content")
			})).appendTo($("body"));
			this.setElement(dialog);
			this.dribbbleShotsView = new DribbbleShotsView();
			$("input.color-code", this.$el).droppable({
				accept: "#dribbble-colors li.color",
				drop: function(event, ui) {
					var code = $("a", ui.draggable).attr("title");
					$(this).val(code).css({backgroundColor: code});
				}
			});

		}
		return this;
	},
	applyColors: function(e) {
		$("form", this.$el).submit();
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
