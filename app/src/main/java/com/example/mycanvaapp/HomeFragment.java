package com.example.mycanvaapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycanvaapp.Adapter.ImageClickListner;
import com.example.mycanvaapp.Adapter.ParentAdapter;
import com.example.mycanvaapp.databinding.FragmentHomeBinding;
import com.example.mycanvaapp.models.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements ParentAdapter.OnItemClickListener {

    private FragmentHomeBinding binding;
    private ParentAdapter parentAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout using ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupMenuAnimation(view);
        setupParentRecyclerView();

        setupBottomNavigation(view);
        return view;
    }

    private void setupMenuAnimation(View view) {
        final View leftMenuLayout = view.findViewById(R.id.left_menu_layout);
        final Animation slideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left);
        final Animation slideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);

        binding.menuIcon.setOnClickListener(v -> {
            if (leftMenuLayout.getVisibility() == View.GONE) {
                leftMenuLayout.setVisibility(View.VISIBLE);
                leftMenuLayout.startAnimation(slideIn);
            } else {
                leftMenuLayout.startAnimation(slideOut);
                slideOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Do nothing
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        leftMenuLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Do nothing
                    }
                });
            }
        });
    }

    private void setupParentRecyclerView() {
        RecyclerView parentRecyclerView = binding.parentRecyclerView;
        parentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create and populate the ParentItem list
        List<ParentItem> parentItemList = new ArrayList<>();
        parentItemList.add(createParentItem());

        // Set the adapter for the parent RecyclerView
        parentAdapter = new ParentAdapter(parentItemList, this, this);
        parentRecyclerView.setAdapter(parentAdapter);
    }

    private ParentItem createParentItem() {
        return new ParentItem(
                " ",
                R.drawable.canvaimage,
                createCategoryItems(),
                createPostersItems(),
                createResumesItems(),
                createPhoneWallpapersItems(),
                createDocsItems(),
                createLogosItems(),
                createInstagramPostsItems()
        );
    }

    @Override
    public void onItemClick(int imageResId) {
        // Handle fragment transaction to navigate to ImageDetailFragment
        ImageDetailFragment fragment = ImageDetailFragment.newInstance(imageResId);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment) // Replace with the container ID
                .addToBackStack(null) // Add to backstack for back navigation
                .commit();
    }

//    @Override
//    public void onItemClick(int imageResId) {
//        // Check if the current fragment is HomeFragment
//        Fragment currentFragment = requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//
//        if (currentFragment instanceof HomeFragment) {
//            // Handle fragment transaction to navigate to ImageDetailFragment
//            ImageDetailFragment fragment = ImageDetailFragment.newInstance(imageResId);
//            requireActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, fragment) // Replace with the container ID
//                    .addToBackStack(null) // Add to backstack for back navigation
//                    .commit();
//        }
//    }



    // Add this method to set up BottomNavigationView
    private void setupBottomNavigation(View view) {
        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottomNavigation);
        FrameLayout fragmentContainer = view.findViewById(R.id.fragment_container); // Ensure correct initialization

        bottomNavigation.setOnItemSelectedListener(item -> {
            fragmentContainer.setVisibility(View.VISIBLE); // Make the fragment container visible

            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                // Handle home navigation if needed
                return true;

            } else if (itemId == R.id.navigation_projects) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProjectsFragment())
                        .addToBackStack(null)
                        .commit();
                return true;

            } else if (itemId == R.id.navigation_templates) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TemplatesFragment())
                        .addToBackStack(null)
                        .commit();
                return true;

            } else if (itemId == R.id.navigation_pro) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new ProFragment())
                        .addToBackStack(null)
                        .commit();
                return true;

            }

            return false; // Return false if no item was handled
        });
    }


    // Sample methods to create lists of items
    private List<CategoryItem> createCategoryItems() {
        List<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem("Social Media", R.drawable.ic_social_media1));
        categoryItems.add(new CategoryItem("Video", R.drawable.ic_vedio));
        categoryItems.add(new CategoryItem("Presentations", R.drawable.ic_presentation1));
        categoryItems.add(new CategoryItem("Print", R.drawable.ic_print1));
        categoryItems.add(new CategoryItem("Documents", R.drawable.ic_doc));
        categoryItems.add(new CategoryItem("Website", R.drawable.ic_website));
        categoryItems.add(new CategoryItem("Books", R.drawable.ic_books));
        return categoryItems;
    }


    private List<PostersItem> createPostersItems() {
        List<PostersItem> postersItems = new ArrayList<>();

        // Create each PostersItem
        PostersItem motivationalPoster = new PostersItem("Motivational Poster", R.drawable.ic_poster3);
        postersItems.add(motivationalPoster);

        PostersItem weddingPoster = new PostersItem("Wedding Poster", R.drawable.ic_poster7);
        postersItems.add(weddingPoster);

        PostersItem birthdayPoster = new PostersItem("Birthday Poster", R.drawable.ic_poster8);
        postersItems.add(birthdayPoster);

        PostersItem homelessnessPoster = new PostersItem("Homelessness Poster", R.drawable.ic_poster1);
        postersItems.add(homelessnessPoster);

        PostersItem congratulationsPoster = new PostersItem("Congratulations Poster", R.drawable.ic_poster2);
        postersItems.add(congratulationsPoster);

        PostersItem salePoster = new PostersItem("Sale Poster", R.drawable.ic_poster4);
        postersItems.add(salePoster);

        PostersItem environmentPoster = new PostersItem("Environment Poster", R.drawable.ic_poster5);
        postersItems.add(environmentPoster);

        PostersItem mothersDayPoster = new PostersItem("Mother's Day Poster", R.drawable.ic_poster6);
        postersItems.add(mothersDayPoster);

        return postersItems;
    }

    private List<ResumesItem> createResumesItems() {
        List<ResumesItem> resumesItems = new ArrayList<>();

        ResumesItem designerResume = new ResumesItem(R.drawable.ic_resume7, "Designer Resume");
        resumesItems.add(designerResume);

        ResumesItem scholarshipResume = new ResumesItem(R.drawable.ic_resume2, "Scholarship Resume");
        resumesItems.add(scholarshipResume);

        ResumesItem collegeResume = new ResumesItem(R.drawable.ic_resume1, "College Resume");
        resumesItems.add(collegeResume);

        ResumesItem salesManagerResume = new ResumesItem(R.drawable.ic_resume8, "Sales Manager Resume");
        resumesItems.add(salesManagerResume);

        ResumesItem highSchoolResume = new ResumesItem(R.drawable.ic_resume3, "High School Resume");
        resumesItems.add(highSchoolResume);

        ResumesItem actingResume = new ResumesItem(R.drawable.ic_resume4, "Acting Resume");
        resumesItems.add(actingResume);

        ResumesItem photoResume = new ResumesItem(R.drawable.ic_resume5, "Photo Resume");
        resumesItems.add(photoResume);

        ResumesItem corporateResume = new ResumesItem(R.drawable.ic_resume6, "Corporate Resume");
        resumesItems.add(corporateResume);

        return resumesItems;
    }


    private List<PhoneWallpapersItem> createPhoneWallpapersItems() {
        List<PhoneWallpapersItem> phoneWallpapersItems = new ArrayList<>();

        PhoneWallpapersItem greenLeafWallpaper = new PhoneWallpapersItem("GreenLeaf Wallpaper", R.drawable.ic_wallpaper1);
        phoneWallpapersItems.add(greenLeafWallpaper);

        PhoneWallpapersItem aestheticWallpaper = new PhoneWallpapersItem("Aesthetic Wallpaper", R.drawable.ic_wallpaper2);
        phoneWallpapersItems.add(aestheticWallpaper);

        PhoneWallpapersItem pinkroseWallpaper = new PhoneWallpapersItem("Pinkrose Wallpaper", R.drawable.ic_wallpaper3);
        phoneWallpapersItems.add(pinkroseWallpaper);

        PhoneWallpapersItem glanceWallpaper = new PhoneWallpapersItem("Glance Wallpaper", R.drawable.ic_wallpaper4);
        phoneWallpapersItems.add(glanceWallpaper);

        PhoneWallpapersItem cuteWallpaper = new PhoneWallpapersItem("Cute Wallpaper", R.drawable.ic_wallpaper5);
        phoneWallpapersItems.add(cuteWallpaper);

        PhoneWallpapersItem animalWallpaper = new PhoneWallpapersItem("Animal Wallpaper", R.drawable.ic_wallpaper6);
        phoneWallpapersItems.add(animalWallpaper);

        PhoneWallpapersItem forestWallpaper = new PhoneWallpapersItem("Forest Wallpaper", R.drawable.ic_wallpaper7);
        phoneWallpapersItems.add(forestWallpaper);

        PhoneWallpapersItem heartBrownWallpaper = new PhoneWallpapersItem("HeartBrown Wallpaper", R.drawable.ic_wallpaper8);
        phoneWallpapersItems.add(heartBrownWallpaper);

        return phoneWallpapersItems;
    }



    private List<DocsItem> createDocsItems() {
        List<DocsItem> docsItems = new ArrayList<>();

        DocsItem thankYou = new DocsItem(R.drawable.ic_document9, "Thank You");
        docsItems.add(thankYou);

        DocsItem weeklySchedule = new DocsItem(R.drawable.ic_document7, "Weekly Schedule");
        docsItems.add(weeklySchedule);

        DocsItem projectPlan = new DocsItem(R.drawable.ic_document6, "Project Plan");
        docsItems.add(projectPlan);

        DocsItem eventProposal = new DocsItem(R.drawable.ic_document1, "Event Proposal");
        docsItems.add(eventProposal);

        DocsItem meetingAgenda = new DocsItem(R.drawable.ic_document2, "Meeting Agenda");
        docsItems.add(meetingAgenda);

        DocsItem managementSystem = new DocsItem(R.drawable.ic_document3, "Management System");
        docsItems.add(managementSystem);

        DocsItem meetingMinute = new DocsItem(R.drawable.ic_document4, "Meeting Minute");
        docsItems.add(meetingMinute);

        DocsItem brandGuidance = new DocsItem(R.drawable.ic_document5, "Brand Guidance");
        docsItems.add(brandGuidance);

        return docsItems;
    }


    private List<InstagramPostsItem> createInstagramPostsItems() {
        List<InstagramPostsItem> instagramPostsItems = new ArrayList<>();

        InstagramPostsItem brownAestheticPost = new InstagramPostsItem(R.drawable.ic_post1, "Brown Aesthetic Post");
        instagramPostsItems.add(brownAestheticPost);

        InstagramPostsItem flawlessSkinPost = new InstagramPostsItem(R.drawable.ic_post2, "Flawless Skin Post");
        instagramPostsItems.add(flawlessSkinPost);

        InstagramPostsItem summerPlanPost = new InstagramPostsItem(R.drawable.ic_post3, "Summer Plan Post");
        instagramPostsItems.add(summerPlanPost);

        InstagramPostsItem newCollectionPost = new InstagramPostsItem(R.drawable.ic_post7, "New Collection Post");
        instagramPostsItems.add(newCollectionPost);

        InstagramPostsItem followMePost = new InstagramPostsItem(R.drawable.ic_post8, "Follow Me Post");
        instagramPostsItems.add(followMePost);

        InstagramPostsItem salePost = new InstagramPostsItem(R.drawable.ic_post4, "Sale Post");
        instagramPostsItems.add(salePost);

        InstagramPostsItem hiringPost = new InstagramPostsItem(R.drawable.ic_post9, "Hiring Post");
        instagramPostsItems.add(hiringPost);

        InstagramPostsItem jewelryPost = new InstagramPostsItem(R.drawable.ic_post6, "Jewelry Post");
        instagramPostsItems.add(jewelryPost);

        return instagramPostsItems;
    }


    private List<LogosItem> createLogosItems() {
        List<LogosItem> logosItems = new ArrayList<>();

        LogosItem customizeLogo = new LogosItem(R.drawable.ic_logo1, "Customize Logo");
        logosItems.add(customizeLogo);

        LogosItem royalHotelLogo = new LogosItem(R.drawable.ic_logo3, "Royal Hotel Logo");
        logosItems.add(royalHotelLogo);

        LogosItem creativeLogo = new LogosItem(R.drawable.ic_logo5, "Creative Logo");
        logosItems.add(creativeLogo);

        LogosItem companyLogo = new LogosItem(R.drawable.ic_logo7, "Company Logo");
        logosItems.add(companyLogo);

        LogosItem beautyLogo = new LogosItem(R.drawable.ic_logo4, "Beauty Logo");
        logosItems.add(beautyLogo);

        LogosItem cookingLogo = new LogosItem(R.drawable.ic_logo8, "Cooking Logo");
        logosItems.add(cookingLogo);

        LogosItem technologyLogo = new LogosItem(R.drawable.ic_logo2, "Technology Logo");
        logosItems.add(technologyLogo);

        LogosItem marketingAgencyLogo = new LogosItem(R.drawable.ic_logo6, "Marketing Agency Logo");
        logosItems.add(marketingAgencyLogo);

        return logosItems;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
