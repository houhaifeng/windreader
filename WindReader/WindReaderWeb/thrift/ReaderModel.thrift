namespace java cn.wind.com.reader.model
struct RssProvider  {
    1: i64 id;
    2: string name;
    3: string url;
    4: string description;
    5: i64 category;
    6: i64 parentId; 
} 	
struct RssContent  {
    1: i64 id;
    2: string title;
    3: string link;
    4: string description;
    5: i64 pubDate;
    6: string guid;
    7: string source;
    8: i64 categoryId;
    9: string category;
    10: string comments;
    11: i64 providerId;
    12: string hash;
}

struct Category {
    1: i64 id;
    2: string title;
    3: string description;
    4: i64 parentId;
}

struct Term {
	1: string term;
	2: double weight;
	3: string description;
}
