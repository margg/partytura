define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/main-page.html'
], function($, _, Backbone, mainPageTemplate){
    var MainPageView = Backbone.View.extend({

        render: function(){
            this.$el.html(_.template(mainPageTemplate));
        }
    });
    return MainPageView;
});