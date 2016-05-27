define([
    'jquery',
    'underscore',
    'backbone',
    'text!templates/twitterConnect.html',
    'text!templates/twitterConnected.html'
], function($, _, Backbone, twitterConnectTemplate, twitterConnectedTemplate){
    var TwitterView = Backbone.View.extend({

        render: function(){
            this.$el.html(_.template(twitterConnectTemplate));
        }
    });
    return TwitterView;
});