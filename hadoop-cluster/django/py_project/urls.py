"""
URL configuration for py_project project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from recommend import views 

urlpatterns = [
    path('admin/', admin.site.urls),
    path('table/', views.recommend),
    path('similar/<str:suser_sns_id>/', views.similar_users),
    path('item/<str:suser_sns_id>/<recent>', views.similar_item),
    path('ingredient/<str:suser_sns_id>', views.ingredient),
    path('delete/<str:suser_sns_id>', views.data_delete),
    path('delete', views.data_all_delete),
    path('test', views.test),
]
