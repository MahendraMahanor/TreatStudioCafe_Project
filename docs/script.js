(function () {
    'use strict';

    // Configurable API base: set data-api-base on <html> or fallback to default
    var apiBase = 'http://localhost:8080';
    var root = document.documentElement;
    if (root && root.getAttribute('data-api-base')) {
        apiBase = root.getAttribute('data-api-base').replace(/\/$/, '');
    }
    var MENU_URL = apiBase + '/api/menu';

    // --- Menu (from API) ---
    var menuList = document.getElementById('menu-list');
    var menuLoading = document.getElementById('menu-loading');
    var menuError = document.getElementById('menu-error');
    var menuErrorText = document.getElementById('menu-error-text');
    var menuEmpty = document.getElementById('menu-empty');
    var menuCategory = document.getElementById('menu-category');
    var menuFilters = document.getElementById('menu-filters');
    var menuRetry = document.getElementById('menu-retry');

    function showMenuState(loading, error, empty) {
        if (menuLoading) menuLoading.hidden = !loading;
        if (menuError) {
            menuError.hidden = !error;
            if (error && menuErrorText) menuErrorText.textContent = menuErrorText.textContent || 'Could not load menu. Please try again later.';
        }
        if (menuEmpty) menuEmpty.hidden = !empty;
        if (menuList) menuList.hidden = !(!loading && !error && !empty);
    }

    function setErrorMsg(msg) {
        if (menuErrorText) menuErrorText.textContent = msg || 'Could not load menu. Please try again later.';
    }

    function escapeHtml(s) {
        var div = document.createElement('div');
        div.textContent = s;
        return div.innerHTML;
    }

    function formatPrice(n) {
        var num = Number(n);
        if (isNaN(num)) return '0';
        return num % 1 === 0 ? num.toString() : num.toFixed(2);
    }

    function buildCategoryOptions(categories) {
        if (!menuCategory) return;
        menuCategory.innerHTML = '<option value="">All</option>';
        categories.forEach(function (cat) {
            var opt = document.createElement('option');
            opt.value = cat;
            opt.textContent = cat;
            menuCategory.appendChild(opt);
        });
    }

    function renderMenuItems(items) {
        if (!menuList) return;
        menuList.innerHTML = '';
        items.forEach(function (item) {
            var card = document.createElement('div');
            card.className = 'menu-card';
            var catHtml = (item.category && item.category.trim()) ? '<span class="menu-card-category">' + escapeHtml(item.category.trim()) + '</span>' : '';
            card.innerHTML =
                (catHtml ? catHtml : '') +
                '<h3>' + escapeHtml(item.name || '') + '</h3>' +
                (item.description ? '<p class="description">' + escapeHtml(item.description) + '</p>' : '') +
                '<p class="price">₹' + formatPrice(item.price) + '</p>';
            menuList.appendChild(card);
        });
    }

    function getUniqueCategories(items) {
        var set = {};
        (items || []).forEach(function (item) {
            var c = (item.category && item.category.trim()) ? item.category.trim() : null;
            if (c) set[c] = true;
        });
        return Object.keys(set).sort();
    }

    var allCategories = [];

    function loadMenu(category) {
        showMenuState(true, false, false);
        var url = MENU_URL;
        if (category && category.trim()) {
            url = url + '?category=' + encodeURIComponent(category.trim());
        }
        fetch(url)
            .then(function (res) {
                if (!res.ok) throw new Error('Menu unavailable');
                return res.json();
            })
            .then(function (data) {
                showMenuState(false, false, !data || data.length === 0);
                if (data && data.length > 0) {
                    renderMenuItems(data);
                    if (!category || !category.trim()) {
                        allCategories = getUniqueCategories(data);
                    }
                    if (allCategories.length > 0) {
                        buildCategoryOptions(allCategories);
                        if (menuCategory) menuCategory.value = (category && category.trim()) ? category.trim() : '';
                        if (menuFilters) menuFilters.hidden = false;
                    }
                } else {
                    if (!category || !category.trim()) {
                        allCategories = [];
                        if (menuFilters) menuFilters.hidden = true;
                    } else if (allCategories.length > 0 && menuCategory) {
                        buildCategoryOptions(allCategories);
                        menuCategory.value = category.trim();
                    }
                }
            })
            .catch(function () {
                showMenuState(false, true, false);
                setErrorMsg('Could not load menu. Make sure the cafe server is running on port 8080.');
            });
    }

    if (menuRetry) {
        menuRetry.addEventListener('click', function () {
            var cat = menuCategory ? menuCategory.value : '';
            loadMenu(cat);
        });
    }
    if (menuCategory) {
        menuCategory.addEventListener('change', function () {
            loadMenu(this.value);
        });
    }

    // --- Mobile nav ---
    var navToggle = document.querySelector('.nav-toggle');
    var mainNav = document.querySelector('.main-nav');
    if (navToggle && mainNav) {
        navToggle.addEventListener('click', function () {
            mainNav.classList.toggle('is-open');
        });
        document.querySelectorAll('.main-nav a').forEach(function (link) {
            link.addEventListener('click', function () {
                mainNav.classList.remove('is-open');
            });
        });
    }

    // --- Smooth scroll ---
    document.querySelectorAll('a[href^="#"]').forEach(function (anchor) {
        anchor.addEventListener('click', function (e) {
            var id = this.getAttribute('href');
            if (id === '#') return;
            var el = document.querySelector(id);
            if (el) {
                e.preventDefault();
                el.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });

    // --- Header scroll ---
    var header = document.querySelector('.site-header');
    if (header) {
        window.addEventListener('scroll', function () {
            header.classList.toggle('scrolled', window.scrollY > 50);
        });
    }

    // --- Run ---
    loadMenu();
})();
