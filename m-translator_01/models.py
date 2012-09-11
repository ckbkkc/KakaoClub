from google.appengine.ext import db

class MyUser(db.Model):
    user = db.UserProperty(required=True)
    nickname = db.StringProperty()
    realname = db.StringProperty()

class Content(db.Model):
    owner = db.ReferenceProperty(MyUser, required=False)
    view_count = db.IntegerProperty(default=0)
    title = db.StringProperty(required=False)
    content = db.TextProperty()
    post_time = db.DateTimeProperty()
    version = db.StringProperty()