define([
    'jquery',
    'underscore',
    'backbone',
    'models/event',
    'text!templates/event.html'
], function($, _, Backbone, Event, eventTemplate){
    var EventView = Backbone.View.extend({

        initialize: function() {
            this.model = new Event({ id: this.id});
            // this.model = new Event({ id: "574ddfa7890465dbc5004c08"});
        },

        render: function(){
            this.model.fetch({
                success: function (model, response, options) {
                    this.renderView();
                }.bind(this)
            });
        },

        renderView: function() {
            this.$el.html(_.template(eventTemplate, {
                model: this.model
            }));
        }

    });
    return EventView;
});