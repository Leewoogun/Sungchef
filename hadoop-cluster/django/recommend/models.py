from django.db import models

# Create your models here.
class Recommend(models.Model):
    suser_sns_id = models.TextField()
    recommend_id = models.IntegerField()
    recommend_type = models.TextField()