define([
    'jquery',
    'underscore',
    'backbone',
    'models/attender',
    'text!templates/dashboard.html'
], function($, _, Backbone, Attender, dashboardTemplate){
    var DashboardView = Backbone.View.extend({

        model: Attender,

        initialize: function() {
            this.model = new Attender({username: "veecla"});
        },

        render: function(){

            this.model.fetch({
                success: function (model, response, options) {
                    this.renderView();
                }.bind(this)
            });
        },

        renderView: function() {
            this.$el.html(_.template(dashboardTemplate, {
                model: this.model
            }));
        }
    });
    return DashboardView;
});