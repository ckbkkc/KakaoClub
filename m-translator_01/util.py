from django.shortcuts import render_to_response
from settings import STATIC_PATH

def err_page(err_msg):
    context = {'static_path':STATIC_PATH,
               'err_msg':err_msg}
    return render_to_response('err_page.html', context)