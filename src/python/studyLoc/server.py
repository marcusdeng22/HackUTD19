#!/usr/bin/env python

import cherrypy
import hashlib
import os
import pymongo as pm

from bson.objectid import ObjectId
from bson.json_util import dumps
from functools import reduce
from mako.lookup import TemplateLookup
from uuid import uuid4, UUID

import datetime

class Root(object):

    def __init__(self):
        templateDir = os.path.join(cherrypy.Application.wwwDir, 'templates')
        cherrypy.log("Template Dir: %s" % templateDir)
        self.templateLookup = TemplateLookup(directories=templateDir)

        client = pm.MongoClient()
        db = client['studyLoc']
        self.users = db['users']
        self.spaces = db['spaces']

    # Pages go below
    @cherrypy.expose
    def index(self):
        #show debug view to test endpoints
        template = self.templateLookup.get_template('debug/DebugApp.html')
        ret = template.render()
        return ret
    # API Functions go below

    '''
    view space json:
    {
        "type": "all", "avail", "unavail"
    }

    space json:
    {
        "name": "name",
        "id": "id",
        "location": "location",
        "details": "details",                   //can expand on this to multiple fields for detailed search
        "availability": "avail", "unavail",
        "time": null, ISODate()
    }

    checkin json:
    {
        "id": "id",
        "duration": x,      //amount of time to reserve in minutes
        "room": "id"
    }

    checkout json:
    {
        "id": "id",
        "room": "id"
    }
    '''

    @cherrypy.expose
    @cherrypy.tools.json_out()
    @cherrypy.tools.json_in()
    def getSpaces(self):
        #query for room info
        #check that we actually have json
        if hasattr(cherrypy.request, 'json'):
            data = cherrypy.request.json
        else:
            raise cherrypy.HTTPError(400, 'No data was given')

        if 'type' in data:
            if data['type'] == 'all':
                #~ info = self.spaces.find()
                #~ print('all')
                #~ print(info)
                #~ print(dumps(info))
                #~ return dumps(info)
                return dumps(self.spaces.find())
            elif data['type'] == 'avail':
                return dumps(self.spaces.find({'availability': 'avail'}))
                #~ print('avail')
            elif data['type'] == 'unavail':
                return dumps(self.spaces.find({'availability': 'unavail'}))
                #~ print('unavail')
        else:
            raise cherrypy.HTTPError(400, "Bad data")

    @cherrypy.expose
    @cherrypy.tools.json_in()
    def addSpace(self):
        #helper method to add spaces
        if hasattr(cherrypy.request, 'json'):
            data = cherrypy.request.json
        else:
            raise cherrypy.HTTPError(400, 'No data was given')

        self.spaces.insert(data)

    @cherrypy.expose
    @cherrypy.tools.json_in()
    @cherrypy.tools.json_out()
    def checkin(self):
        if hasattr(cherrypy.request, 'json'):
            data = cherrypy.request.json
        else:
            raise cherrypy.HTTPError(400, 'No data was given')

        if 'id' in data and 'duration' in data and 'room' in data:
            self.spaces.update_one({"id": data["room"]}, {"$set": {"availability": "unavail", "time": datetime.datetime.now() + datetime.timedelta(minutes=data["duration"])}})
        else:
            raise cherrypy.HTTPError(400, "Bad data")

    @cherrypy.expose
    @cherrypy.tools.json_in()
    @cherrypy.tools.json_out()
    def checkout(self):
        if hasattr(cherrypy.request, 'json'):
            data = cherrypy.request.json
        else:
            raise cherrypy.HTTPError(400, 'No data was given')

        if 'id' in data and 'room' in data:
            self.spaces.update_one({"id": data["room"]}, {"$unset": {"time": ""}, "$set": {"availability": "avail"}})
        else:
            raise cherrypy.HTTPError(400, "Bad data")

def main():
    cherrypy.Application.wwwDir = os.path.join(os.path.dirname(os.path.realpath(__file__)), 
        os.path.join('..', '..', 'www'))

    server_config = os.path.abspath(os.path.join(
        os.path.dirname(os.path.realpath(__file__)), 
        '..', '..', 'etc', 'server.conf'))
    cherrypy.tree.mount(Root(), '/', config=server_config)

    cherrypy.engine.start()
    input()
    cherrypy.engine.stop()

if __name__ == '__main__':
    main()
