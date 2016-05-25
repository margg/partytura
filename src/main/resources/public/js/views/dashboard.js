define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/dashboard.html'
], function($, _, Backbone, dashboardTemplate){
    var DashboardView = Backbone.View.extend({

        render: function(){
            this.$el.html(_.template(dashboardTemplate));
        }
    });
    return DashboardView;
});