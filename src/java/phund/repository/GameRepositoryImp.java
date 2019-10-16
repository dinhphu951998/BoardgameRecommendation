/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phund.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import phund.entity.Game;
import phund.entity.Image;
import phund.entity.SuggestedGame;
import phund.entity.TrendGame;
import phund.entity.VotedGame;
import phund.utils.JPAUtils;

/**
 *
 * @author PhuNDSE63159
 */
public class GameRepositoryImp extends BaseRepositoryImp<Game, Integer> implements GameRepository {

    private final String GET_SUGGESTED_GAME_USER_BASED
            = "select u.Similarity as 'Similarity', v.Point as 'PrefPoint', g.id, g.title, g.thumbnail "
            + "from UserBasedPoint u join Vote v on u.PrefId = v.UserId join Game g on g.Id = v.GameId "
            + "where u.UserId = ?userId and g.id not in (select v.GameId from Vote v where v.UserId = ?userId) "
            + "order by u.Similarity desc, v.Point desc, v.Time desc";

    private final String GET_SUGGESTED_GAME_ITEM_BASED
            = "select g.Id, g.Title, g.Thumbnail, X.XPoint as matchingPercent "
            + "from Game g join (select  i.PrefId, sum(i.Similarity * X.Point) / sum(i.Similarity) * 100 /5 as 'XPoint' "
            + "				from [ItemBasedPoint] i, (select g.id, v.Point "
            + "							from Game g, Vote v "
            + "							where g.id = v.gameId and v.UserId = ?userId) X "
            + "				where i.ItemId = X.Id "
            + "                                     and i.PrefId not in (select g.id "
            + "                                                          from Game g, Vote v "
            + "                                                          where g.id = v.gameId and v.UserId = ?userId) "
            + "				group by i.PrefId) X "
            + "             on g.Id = X.PrefId "
            + "order by X.XPoint desc";

    private final String GET_VOTED_GAMES
            = "select g.Id, g.Title, g.Thumbnail, v.Point from Game g join Vote v on g.id = v.GameId where v.UserId = ?userId";

    private final String GET_TREND_GAMES
            = "SELECT g.id, g.title, g.thumbnail, g.ratingPoint FROM Game g ORDER BY g.ratingPoint desc";

    private final String SEARCH_GAMES
            = "SELECT g.id, g.title, g.thumbnail, g.ratingPoint FROM Game g where g.title like ?searchValue "
            + "ORDER BY g.ratingPoint desc";

    private final int DEFAULT_FETCH = 40;

    public GameRepositoryImp() {
        super(Game.class);
    }

    @Override
    public void createRange(List<Game> entities) {
        for (Game game : entities) {
            addImage(game);
        }
        super.createRange(entities); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game create(Game entity) {
        addImage(entity);
        return super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    protected void addImage(Game game) {

        if (game.getImages() != null) {
            List<Image> images = new ArrayList<>(game.getImages());
            for (Image image : images) {
                image.setGameId(game);
            }
        }

    }

    public List<VotedGame> getVotedGame(int userId, Integer offset, Integer fetch) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_VOTED_GAMES, "VotedGame");
            query.setParameter("userId", userId);

            if (offset == null) {
                offset = 0;
            }
            if (fetch == null) {
                fetch = DEFAULT_FETCH;
            }
            query.setFirstResult(offset * fetch);
            query.setMaxResults(fetch);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    public List<SuggestedGame> getSuggestedGamesUserBased(int userId, Integer offset, Integer fetch) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_SUGGESTED_GAME_USER_BASED, "SuggestedGame");
            query.setParameter("userId", userId);
//            query.setParameter("prefId", prefId);
            if (offset == null) {
                offset = 0;
            }
            if (fetch == null) {
                fetch = DEFAULT_FETCH;
            }
            query.setFirstResult(offset * fetch);
            query.setMaxResults(fetch);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    public List<SuggestedGame> getSuggestedGamesItemBased(int userId, Integer offset, Integer fetch) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(GET_SUGGESTED_GAME_ITEM_BASED, "SuggestedGame");
            query.setParameter("userId", userId);

            if (offset == null) {
                offset = 0;
            }
            if (fetch == null) {
                fetch = DEFAULT_FETCH;
            }
            query.setFirstResult(offset * fetch);
            query.setMaxResults(fetch);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    @Override
    public List<TrendGame> getTrendGames(Integer offset, Integer fetch) {
        em = JPAUtils.getEntityManager();
        try {
//            Query query = em.createNamedQuery("Game.findVotedGame", VotedGame.class);
            Query query = em.createNativeQuery(GET_TREND_GAMES, "TrendGame");

            if (offset == null) {
                offset = 0;
            }
            if (fetch == null) {
                fetch = DEFAULT_FETCH;
            }
            query.setFirstResult(offset * fetch);
            query.setMaxResults(fetch);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

    public List<TrendGame> searchGames(String searchValue, Integer offset, Integer fetch) {
        em = JPAUtils.getEntityManager();
        try {
            Query query = em.createNativeQuery(SEARCH_GAMES, "TrendGame");
            query.setParameter("searchValue", "%" + searchValue + "%");

            if (offset == null) {
                offset = 0;
            }
            if (fetch == null) {
                fetch = DEFAULT_FETCH;
            }
            query.setFirstResult(offset * fetch);
            query.setMaxResults(fetch);

            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            closeConnection();
        }
        return null;
    }

}
