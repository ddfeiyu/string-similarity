# string-similarity
java算法（1）---余弦相似度计算字符串相似率  

1、功能需求：最近在做通过爬虫技术去爬取各大相关网站的新闻，储存到公司数据中。

这里面就有一个技术点，就是如何保证你已爬取的新闻，再有相似的新闻或者一样的新闻，那就不存储到数据库中。
（因为有网站会去引用其它网站新闻，或者把其它网站新闻拿过来稍微改下内容就发布到自己网站中）。 

2、解析方案：最终就是采用余弦相似度算法，来计算两个新闻正文的相似度。现在自己写一篇博客总结下。
- [余弦相似度算法](https://www.cnblogs.com/qdhxhz/p/9484274.html)