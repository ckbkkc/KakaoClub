from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^appinsight/', include('appinsight.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # (r'^admin/', include(admin.site.urls)),

    (r'^$', 'views.root'),
    (r'^user/$', 'views.user_index'),
    (r'^user/login/$', 'views.login'),
    (r'^content/new/$', 'views.new_content'),
    (r'^content/(?P<content_id>\d+)/read/$', 'views.read_content'),
    (r'^content/(?P<content_id>\d+)/modify/$', 'views.modify'),
    (r'^content/(?P<content_id>\d+)/delete/$', 'views.delete'),
    (r'^api/notice/$', 'views.notice_json'),
)
