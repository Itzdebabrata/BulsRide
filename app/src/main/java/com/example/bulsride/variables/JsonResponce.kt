package com.example.bulsride.variables

class JsonResponce (val status:String,val totalResults:String,val articles:List<Article> )
class Article(val source:Source,val author:String,val title:String,val description:String,val url:String,val urlToImage:String,val publishedAt:String,val content:String)
class Source(val source:String,val id:String,val name:String)
