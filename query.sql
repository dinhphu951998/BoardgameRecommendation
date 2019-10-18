---------------
select * from Game order by id desc

-------------------------------
select * from Vote 
order by Time desc

--------------------------------
select v.GameId as 'GameId', v.UserId as 'UserId', v.Point as 'UserPoint', v1.UserId as 'PrefId', v1.Point as 'PrefPoint'
from Vote v join Vote v1 on v.GameId = v1.GameId
where v.UserId = 1 and v1.UserId = 2

---------------------------------
SELECT deqs.last_execution_time AS [Time], dest.text AS [Query], dest.*
FROM sys.dm_exec_query_stats AS deqs
CROSS APPLY sys.dm_exec_sql_text(deqs.sql_handle) AS dest
WHERE dest.dbid = DB_ID('master')
ORDER BY deqs.last_execution_time DESC

----------------------------------
-- tập item voted
-- chọn những top 10 prefId của itemVoted
select top 10 g.*
from ItemBasedPoint i join Game g on i.PrefId = g.Id
where i.ItemId in (60,100,200)
order by i.Similarity desc
 

-- USER BASED: chọn những game (vote cao, k common) của những user(similarity cao) với user 1
select u.Similarity as 'Similarity', v.Point as 'PrefPoint', g.*
from UserBasedPoint u join Vote v on u.PrefId = v.UserId join Game g on g.Id = v.GameId
where u.UserId = 3 and g.id not in (select v.GameId from Vote v where v.UserId = 3)
order by u.Similarity desc, v.Time desc, v.Point desc

select  v.UserId as 'UserId', v.GameId as 'GameId', v.Point as 'GamePoint', v1.UserId as 'PrefId', v1.Point as 'PrefPoint'
from Vote v join Vote v1 on v.UserId = v1.UserId
where v.GameId = 165 and v1.GameId = 166


-- ITEM BASED: chọn game (vote cao của user đó) -> lấy game (sim cao so với game đó)
select i.Similarity as 'Similarity', v.Point as 'PrefPoint', g.id, g.title, g.Thumbnail
from ItemBasedPoint i join Vote v on i.ItemId = v.GameId join Game g on i.PrefId = g.Id
where v.UserId = 1 
order by i.Similarity desc, v.Point desc, v.Time desc

select *
from ItemBasedPoint i join Vote v on i.ItemId = v.GameId join Game g on i.PrefId = g.Id 
where v.UserId = 3 and i.PrefId not in (select GameId from Vote where UserId = 3) 
order by i.Similarity desc, v.Point desc, v.Time desc 



select i.PrefId
from Vote v join ItemBasedPoint i on v.GameId = i.ItemId
where v.UserId  = 3 and i.PrefId not in (select GameId from Vote where UserId = 3)
group by i.PrefId


select top 3 *
from ItemBasedPoint 
group by itemId
order by Similarity desc


select *
from  (select * from Vote v where v.userId = 3) vv 
				join ItemBasedPoint ii 
				on vv.GameId = ii.ItemId
order by vv.time desc, vv.point desc, ii.Similarity desc



select * 
from ItemBasedPoint i 
order by i.Similarity desc


select * from Game 
where id  =87

select g.id
from Game g join Vote v on g.id = v.gameid

having count(v.UserId) > 0

select g.Id, g.Title, g.Thumbnail, v.Point from Game g join Vote v on g.id = v.GameId where v.UserId = 3

select * from Game where id in (193,178)

