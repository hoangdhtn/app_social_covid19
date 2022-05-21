package com.huyhoang.covid19.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.huyhoang.covid19.entities.Posts;
import com.huyhoang.covid19.entities.Posts_Cmt;
import com.huyhoang.covid19.entities.Posts_Img;
import com.huyhoang.covid19.entities.Users;

@Repository
public class PostsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthDAO authDAO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPosts(int position, int pageSize) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where enabled = 1 order by id desc";
		Query query = session.createQuery(hql, Posts.class);
		query.setFirstResult(position);
		query.setMaxResults(pageSize);

		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPostsUser(Integer id_user) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where enabled = 1 and id_user = :id_user order by id desc";
		Query query = session.createQuery(hql, Posts.class);
		query.setParameter("id_user", id_user);
		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts> getAllPostsInWall(Integer id_user) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from Posts where id_user = :id_user order by id desc";
		Query query = session.createQuery(hql, Posts.class);
		query.setParameter("id_user", id_user);

		List<Posts> list = query.list();

		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public Posts addPost(String username, Posts data, MultipartFile[] files) {
		Session session = sessionFactory.getCurrentSession();

		Users user = authDAO.loadUsername(username);
		Date date = new Date();
		Posts post = new Posts();

		Set<Posts_Img> posts_Imgs = new HashSet<>();

		// Upload hình ảnh
		File localfile = new File("");
		String uploadRootPath = localfile.getAbsolutePath() + "\\uploads\\";
		System.out.println("Current working directory : " + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}

		if(files != null && files.length > 0) {
			MultipartFile[] fileDatas = files;

			for (MultipartFile fileData : fileDatas) {

				// Tên file gốc tại Client.
				String name = fileData.getOriginalFilename();
				System.out.println("Client File Name = " + name);

				if (name != null && name.length() > 0) {
					try {
						// Tạo file tại Server.
						String changeName = LocalTime.now().toString().replaceAll("[^A-Za-z0-9]", "");
						String nameupString = changeName + name;
						File serverFile = new File(
								uploadRootDir.getAbsolutePath() + File.separator + nameupString.toLowerCase());

						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(fileData.getBytes());
						stream.close();
						//
						Posts_Img img = new Posts_Img();
						img.setName(nameupString);
						img.setPost(post);
						img.setCreated_at(date);
						img.setUpdated_at(date);
						posts_Imgs.add(img);

						System.out.println("Write file: " + serverFile);
					} catch (Exception e) {
						System.out.println("Error Write file: " + name);
					}
				}
			}
		}

		// System.out.println("Hinh anh ne : " + posts_Imgs);
		try {
			post.setContent(data.getContent());
			post.setUser(user);
			post.setEnabled(true);
			post.setCreated_at(date);
			post.setUpdated_at(date);
			
			if(files != null && files.length > 0) {
				post.setPosts_imgs(posts_Imgs);
			}else {
				post.setPosts_imgs(null);
			}
			session.save(post);
			return post;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Loi ne : " + e);
			return null;

		}

	}

	public Posts updatePost(String username, Posts postForm) {
		Session session = sessionFactory.getCurrentSession();

		Date date = new Date();
		Posts post = session.get(Posts.class, postForm.getId());
		Users user = authDAO.loadUsername(username);

		Users user_post = post.getUser();
		if (post != null && user_post.getId() == user.getId()) {
			post.setContent(postForm.getContent());
			post.setEnabled(postForm.getEnabled());
			post.setUpdated_at(date);
			session.save(post);
			return post;
		} else {
			return null;
		}

	}

	public Boolean deletePost(String username, Integer id_post) {
		Session session = sessionFactory.getCurrentSession();
		Users user = authDAO.loadUsername(username);
		try {
			Posts post = session.get(Posts.class, id_post);
			Users user_post = post.getUser();

			if (post != null && user.getId() == user_post.getId()
					|| AuthorityUtils
							.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
							.contains("ROLE_ADMIN")
					|| AuthorityUtils
							.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
							.contains("ROLE_MODERATOR")) {
				Set<Posts_Img> posts_Imgs = post.getPosts_imgs();

				for (Posts_Img img : posts_Imgs) {
					try {
						Files.deleteIfExists(Paths.get("uploads", img.getName()));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				session.delete(post);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Posts_Cmt> getCommentPost(Integer id_post) {
		Session session = sessionFactory.getCurrentSession();

		try {
			String hql = "from Posts_Cmt where id_post = :id_post order by id DESC";
			Query query = session.createQuery(hql, Posts_Cmt.class);
			query.setParameter("id_post", id_post);

			List<Posts_Cmt> list = query.list();
			if (list != null & list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public Boolean addCommentPost(String username, Integer id_post, Posts_Cmt data) {
		Session session = sessionFactory.getCurrentSession();

		Date date = new Date();
		Users user = authDAO.loadUsername(username);
		Posts post = session.get(Posts.class, id_post);

		if (post != null) {
			try {
				Posts_Cmt pCmt = new Posts_Cmt();
				pCmt.setContent(data.getContent());
				pCmt.setPost(post);
				pCmt.setUser(user);
				pCmt.setCreated_at(date);
				pCmt.setUpdated_at(date);
				session.save(pCmt);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		} else {
			return false;
		}
	}

	public Boolean deleteCommentPost(String username, Integer id_cmt) {
		Session session = sessionFactory.getCurrentSession();
		Users user = authDAO.loadUsername(username);

		try {
			Posts_Cmt posts_Cmt = session.get(Posts_Cmt.class, id_cmt);
			if (posts_Cmt.getUser().getId() == user.getId()
					|| AuthorityUtils
							.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
							.contains("ROLE_ADMIN")
					|| AuthorityUtils
							.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities())
							.contains("ROLE_MODERATOR")) {
				session.clear();
				
				session.createQuery("delete from Posts_Cmt where id = :id_cmt")
				.setParameter("id_cmt", id_cmt).executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
