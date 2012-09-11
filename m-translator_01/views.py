# coding: utf-8
from datetime import datetime
from django import forms
from django.core.context_processors import csrf
from django.http import HttpResponseRedirect, HttpResponse
from django.shortcuts import render_to_response
from django.utils import simplejson
from google.appengine.api import users
from settings import STATIC_PATH
from util import err_page
import logging
import models
import os
import sys

def root(request):
    user_info = users.get_current_user()
    try:
        c = models.Content.all().order('-post_time').fetch(limit=10)
    except Exception, x:
        logging.error('Error, root, %s', x)
        return
    
    context = {'static_path':STATIC_PATH,
               'user_info':user_info,
               'contents':c}
    return render_to_response('index.html', context)

# login 
def login(request):
    user_info = users.get_current_user()
    if not user_info:
        return HttpResponseRedirect(users.create_login_url('/user/login/'))
    try:
        u = models.MyUser.all().filter('user =', user_info).get()
        if u:
            return HttpResponseRedirect('/')
        else:
            u = models.MyUser(user=user_info,
                              nickname = user_info.nickname())
            u.put()
            return HttpResponseRedirect('/user/')
    except Exception:
        return err_page(('login_Error'))
    
    
def user_index(request):
    user_info = users.get_current_user()
    if not user_info:
        return HttpResponseRedirect('/user/login/')
    if request.method == 'GET':
        logging.error('method - get')
        u = models.MyUser.all().filter('user = ', user_info).get()
        f_data = {'real_name':u.realname }
        u_form = UserInfoForm(f_data)
        context = {'static_path':STATIC_PATH,
                   'user':u,
                   'user_info':user_info,
                   'form':u_form,
                   'csrfmiddlewaretoken': str(csrf(request)['csrf_token'])}
        return render_to_response('user_modify.html', context)
    if request.method == 'POST':
        logging.error('method - post')
        try:
            u_form = UserInfoForm(request.POST)
            if not u_form.is_valid():
                return err_page(('Error'))
            
            u = models.MyUser.all().filter('user = ', user_info).get()
            u.nickname = user_info.nickname()
            u.realname = request.POST.get('real_name')
            u.put()
            return HttpResponseRedirect('/user/')
        except Exception, x:
            return err_page(('user_index_POST_Error'));
    
def new_content(request):
    user_info = users.get_current_user()
    if not user_info:
        return HttpResponseRedirect('/user/login/')
    
    if request.method == 'GET':
        c_form = ContentForm(auto_id=True)
        context = {'static_path':STATIC_PATH,
                   'user_info':user_info,
                   'form':c_form}
        return render_to_response('content_new.html', context)
    if request.method == 'POST':
        try:
            w_form = ContentForm(request.POST)
            if not w_form.is_valid():
                return err_page('error_new_content_POST_form is valid')
            u = models.MyUser.all().filter('user = ', user_info).get()
            if not u:
                return err_page('error_new_content_POST invalid User')
            c = models.Content(
                        owner = u,
                        title = request.POST['title'].strip(),
                        content = request.POST['content'].strip(),
                        version = request.POST['version'].strip(),
                        #title = unicode(request.POST['title'].strip(), 'utf8'),
                        #content = unicode(request.POST['content'].strip(), 'utf8'),
                        #version = unicode(request.POST['version'].strip(), 'utf8'),
                        post_time = datetime.now())
            c.put()
            return HttpResponseRedirect('/content/'+str(c.key().id())+'/read/')
        except Exception, x:
            logging.error('error_new_content_POST,  %s', x)
            return err_page('error_new_content_POST')
    return err_page('error_new_content ::: Wrong request')

def read_content(request, content_id):
    if request.method == 'GET':
        user_info = users.get_current_user()
        c = models.Content.get_by_id(int(content_id))
        if not c:
            return err_page('Wrong Request')
        context = {'static_path':STATIC_PATH,
                   'user_info':user_info,
                   'content':c}
        return render_to_response('content_read.html', context)
    return err_page('Wrong Request2')

def modify(request, content_id):
    user_info = users.get_current_user()
    if not user_info:
        return HttpResponseRedirect('/user/login/')
    if request.method == 'GET':
        c = models.Content.get_by_id(int(content_id))
        if not c:
            return err_page('Wrong request : modify_GET')
        if c.owner.user != user_info:
            return err_page('You cannot modify this information')
        form_data = {'title':c.title,
                     'content':c.content,
                     'version':c.version}
        c_form = ContentForm(form_data, auto_id=True)
        context = {'static_path':STATIC_PATH,
                   'user_info':user_info,
                   'form':c_form}
        return render_to_response('content_modify.html', context)
    if request.method == 'POST':
        try:
            c_form = ContentForm(request.POST)
            if not c_form.is_valid():
                return err_page('Invalid input.')
            
            u = models.MyUser.all().filter('user = ', user_info).get()
            if not u:
                return err_page('Invalid user.')
            c = models.Content.get_by_id(int(content_id))
            if not c:
                return err_page('Wrong request')
            
            if c.owner.user != user_info:
                return err_page('You cannot modify this information.')
            
            c.nickname = u.nickname
            c.title = request.POST['title'].strip()
            c.content = request.POST['content'].strip()
            c.version = request.POST['version'].strip()
            c.post_time = datetime.now()
            c.put()
            
            return HttpResponseRedirect('/content/'+str(c.key().id())+'/read/')
        except Exception, x:
            return err_page('Error')

def delete(request, content_id):
    user_info = users.get_current_user()
    if not user_info:
        return HttpResponseRedirect('/user/login/')
    if request.method == 'GET':
        try:
            c = models.Content.get_by_id(int(content_id))
            if not c:
                return err_page('Wrong request')
            if c.owner.user != user_info:
                return err_page('You cannot delete this information.')
            c.delete()
            return HttpResponseRedirect('/')
        except Exception, x:
            return err_page('Delete Error')
    return err_page('Invalid request.')

class UserInfoForm(forms.Form):
    real_name = forms.CharField(label=('Real Name'),
                                required=False,
                                widget=forms.TextInput(attrs={'size':'20'})
                                )
    
class ContentForm(forms.Form):
    title = forms.CharField(label=('Title'),
                            widget=forms.TextInput(attrs={'size':'54',
                                                          'maxlength':'100'}))
    content = forms.CharField(label=('Content'),
                              widget=forms.Textarea(attrs={'size':'2048',
                                                           'limit':'2048',
                                                           'cols':'40',
                                                           'rows':'10',
                                                           'wrap':'hard',
                                                           'class':'required'}))
    version = forms.CharField(label=('Version'),
                              widget=forms.TextInput(attrs={'size':'30',
                                                            'maxlength':'50'}))
    
def notice_json(request):
    try:
        notice = models.Content.all().order('-post_time').fetch(limit=1)
        data =[]
        for row in notice:
            attr = {}
            #attr['id'] = row.key().id()
            attr['title'] = row.title
            attr['content'] = row.content
            attr['version'] = row.version
            data.append(attr)
        result = {'result':'Success',
                  'request':'Notice',
                  'data':data}
        json = simplejson.dumps(result, sort_keys=True, indent=4) # sort_keys, indent의 용도는?
        return HttpResponse(json)
    
    except Exception, x:
        logging.error(x)
        result = {'result':'Failure', 
                  'request':'Notice'}
        json = simplejson.dumps(result, sort_keys=True, indent=4)
        return HttpResponse(json)
    return err_page('Invalid request.')
        
    