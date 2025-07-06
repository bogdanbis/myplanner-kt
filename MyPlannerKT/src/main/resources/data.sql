INSERT INTO public.application_user (id, email, first_name, last_name, password)
VALUES ('96e286a4-4e8b-42d7-ae00-8555e20b455c', 'matteo@email.com', 'Matteo', 'Olsen',
		'$2a$10$jufYK6htLZjPX9jiOAoDXuH305vCh9Vi4fEi.8Fbvl3fEnv2qsTYO');
INSERT INTO public.application_user (id, email, first_name, last_name, password)
VALUES ('404947c7-47df-47da-a635-dafe2da76f50', 'bogdan@email.com', 'Bogdan', 'Negoita',
		'$2a$10$rr.FGd3T7C2hEfL7fdU7c.LDWuBP0YJhxGj9tc.Sguq4rgYlUQQLK');

INSERT INTO public.application_user_ui_preferences (id, pinned_plans, user_id)
VALUES ('bfac55a5-14e2-415e-aea6-de7fdf01e26f', null, '404947c7-47df-47da-a635-dafe2da76f50');
INSERT INTO public.application_user_ui_preferences (id, pinned_plans, user_id)
VALUES ('afc414bd-eb95-4ce2-8ef0-9185794f3f31', null, '96e286a4-4e8b-42d7-ae00-8555e20b455c');

INSERT INTO public.plan (id, color, created_at, description, is_public, last_modified_at, short_description, title,
						 author_id)
VALUES ('2abb24ec-afd3-42cf-a4b7-8daf0680cdf6', '#5856D6', '2025-07-06 13:02:57.606431',
		'Transcendental Meditation (TM) is a simple, silent technique that involves repeating a personalized mantra to quiet the mind and enter a deep state of rest and awareness. Unlike concentration-based methods, TM does not require effort or control of thoughts. Practiced for 15–20 minutes twice a day, it can reduce stress, improve focus, and enhance emotional balance. TM is typically taught by certified instructors, but the basic process can be understood and adapted independently. This plan offers a foundational understanding and gradual entry into regular TM practice.',
		true, '2025-07-06 13:10:04.091721',
		'Transcendental Meditation (TM) is a simple, effortless technique where you silently repeat a mantra to settle the mind into a deep state of rest and inner calm. It''s practiced for 15–20 minutes twice a day to reduce stress and enhance mental clarity.',
		'Transcendental Meditation', '96e286a4-4e8b-42d7-ae00-8555e20b455c');
INSERT INTO public.plan (id, color, created_at, description, is_public, last_modified_at, short_description, title,
						 author_id)
VALUES ('809e0499-592a-4182-b869-016c4a960156', '#5856D6', '2025-07-06 12:37:15.904232', 'Pottery - the craft of shaping clay into functional or artistic objects using techniques like hand-building or wheel-throwing. Once formed, pieces are fired in a kiln to harden and can be decorated with glazes or paints. Pottery is both a creative expression and a practical skill.

A beginner-friendly plan to help you start your journey into pottery, from getting the right tools to making your first pieces. No prior experience needed.

Duration: ~6–8 weeks (flexible)

Goal: Understand what pottery is and explore different styles (wheel-throwing, hand-building, sculptural).', true,
		'2025-07-06 12:39:51.276627',
		'A beginner-friendly plan to help you start your journey into pottery, from getting the right tools to making your first pieces. No prior experience needed.',
		'Pottery as a hobby', '404947c7-47df-47da-a635-dafe2da76f50');
INSERT INTO public.plan (id, color, created_at, description, is_public, last_modified_at, short_description, title,
						 author_id)
VALUES ('434767a0-314d-49d5-8d46-ee46ba96d186', '#5856D6', '2025-07-06 12:48:16.724272',
		'A 3-Day Workout Plan made only with weird, unconventional, or lesser-known exercises — but still functional and fun. It’s designed to be quirky, engaging, and to hit different muscle groups with a twist. Perfect for spicing up your routine.',
		true, '2025-07-06 12:48:43.858916', 'An alternative to the most popular workout routine. Spice it up.',
		'Weird Workout (3 days/week)', '404947c7-47df-47da-a635-dafe2da76f50');

INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('5ccce5fd-bef4-4c44-9eb5-1f2c15fb21ba', 1, 'Get a mantra to use during practice.
Authentic TM training includes mantra selection based on age/gender.', 1, 'Choose or Receive a Mantra', null,
		'2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('22bb0cb9-ad8c-4064-94b3-59fceb574beb', 0, 'Learn under guidance and access studio tools.', 4,
		'Take a Beginner Pottery Class (Optional but Recommended)', null, '809e0499-592a-4182-b869-016c4a960156');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('95eb7403-40c2-4cf1-ae4c-d1916b558ab0', 0, 'Get beginner-level pottery tools and clay.
Resources:
- Local craft stores or online shops (e.g., Amazon, Blick Art Materials)
Estimated time: 2–5 days', 1, 'Gather Basic Materials & Tools', null, '809e0499-592a-4182-b869-016c4a960156');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('2f683bd8-dddb-4548-9351-ff4b8301509e', 0, 'Create a space for regular practice.
Invest in a small kiln or wheel if you’re committed.
Estimated time: 2–3 days', 2, 'Set Up a Simple Home Practice Area', null, '809e0499-592a-4182-b869-016c4a960156');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('967f7c6e-9fd8-42df-aab0-5d460fa315b2', 0, 'Understand what pottery is and explore different styles (wheel-throwing, hand-building, sculptural).
Resources:
- YouTube - Simon Leach Pottery -  Pinterest board on pottery styles
Estimated time: 2–3 days', 0, 'Research and Get Inspired', null, '809e0499-592a-4182-b869-016c4a960156');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('9384e855-a088-4236-a10e-de5319740b59', 0, null, 0, 'Day 1: Push (Chest, Shoulders, Triceps)', null,
		'434767a0-314d-49d5-8d46-ee46ba96d186');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('2f85821d-e8fc-4da2-8d6d-74af9e89b693', 0, null, 1, 'Day 2: Pull (Back, Biceps, Grip)', null,
		'434767a0-314d-49d5-8d46-ee46ba96d186');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('f3bc8876-18c2-4650-9c99-1dd2bd2b6589', 0, null, 2, 'Day 3: Legs (Quads, Glutes, Hamstrings, Calves)', null,
		'434767a0-314d-49d5-8d46-ee46ba96d186');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('2a9b318f-d6a2-453d-b534-2f10d49a93ee', 0, 'Deepen practice with certified guidance.
Look for nearby TM centers or online courses.
Learn advanced techniques and receive personalized feedback.', 5, 'Consider Formal Instruction', null,
		'2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('d264dc79-3dff-446f-85e6-8cbf61b5a773', 1, 'Grasp what TM is and how it differs from other meditations.
Resources:
- Videos: “Intro to Transcendental Meditation” by TM.org
- Articles by Maharishi Mahesh Yogi
Time: 1–2 days', 0, 'Understand the Core Concept', null, '2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('754884c2-013c-47da-9fc5-05acd7bb7c76', 0, 'Practice basic methods without a wheel.
Estimated time: 1–2 weeks', 3, 'Learn Basic Handbuilding Techniques', null, '809e0499-592a-4182-b869-016c4a960156');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('d1cfe84c-7e94-47f7-904a-7ea115fe1aad', 0, 'Notice shifts in stress, focus, and well-being.
Journal after each session.
Observe mood, sleep, clarity, and energy levels.
Time: 1 week of observation', 3, 'Track Internal Effects and Changes', null, '2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('a2858f2b-455e-4e6c-8610-a796856907be', 0, 'Begin the practice of TM using your mantra.
Sit comfortably with eyes closed.
Silently repeat the mantra gently.
When thoughts come, gently return to the mantra without effort.
Do this for 15–20 minutes, twice daily.
Time: 1 week of daily practice', 2, 'Practice the Technique', null, '2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('57b9d76d-640e-4efd-b451-dea105248f11', 1, 'Create a consistent, sustainable meditation habit.
Set specific times: ideally morning and evening.
Use a timer or app for 20-minute sessions.
Create a quiet, distraction-free space.
Optional: Add rituals like lighting a candle or playing ambient sounds.', 4, 'Build a Routine', null,
		'2abb24ec-afd3-42cf-a4b7-8daf0680cdf6');
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('e51afd0e-b1f7-4015-aeac-a9b8111714f5', 0, 'Optional: Consider a starter pottery kit.', 0,
		'Buy basic tools: sponge, rib, needle tool, loop tool.', '95eb7403-40c2-4cf1-ae4c-d1916b558ab0', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('b46c5fb7-6a81-4461-9056-b5c370cc31b5', 0, 'e.g., air-dry for starters or stoneware if you plan to use a kiln',
		1, 'Choose clay', '95eb7403-40c2-4cf1-ae4c-d1916b558ab0', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('c4cc4ada-7784-4641-8140-6201cdcb840b', 0, null, 0, 'Find local community centers or pottery studios.',
		'22bb0cb9-ad8c-4064-94b3-59fceb574beb', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('7216cff2-5669-419e-821e-f0989200b290', 0, null, 1, 'Enroll in a course or weekend workshop.',
		'22bb0cb9-ad8c-4064-94b3-59fceb574beb', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('23cb3a7e-1be8-44ec-8141-22cdbd969deb', 0, null, 1, 'Store your tools and clay properly.',
		'2f683bd8-dddb-4548-9351-ff4b8301509e', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('6f05786b-d9cc-4bfb-b8a7-f6e933bcd0e6', 0, 'A mix of yoga and strength; explosive arch to dive.', 0,
		'Explosive Hindu Push-Ups - 4 sets x 6–8', '9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('8028cc0a-bc5b-4eab-a0ba-c3c4ee41bd3b', 0, 'Face-up, hips elevated, bend arms to dip.', 2,
		'Crab Bridge Tricep Dips - 3 sets x 10–12', '9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('540694a6-c2fd-4a71-bad0-7099cc8e88e0', 0, 'Like Hindu push-ups but swooping low to high.', 3,
		'Dive Bomber Push-Ups - 3 sets x 8', '9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('9d2e54bd-b054-4720-a41f-efd4e75e877b', 0, 'Bear crawl position, walk backward, tap opposite shoulder.', 4,
		'Reverse Bear Crawl Shoulder Taps - 3 sets x 20 seconds', '9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('524275e2-9f47-4b02-9854-84486a3ccfbc', 0,
		'Weird finisher: band around your back, slap palms forward like claps.', 5,
		'Elastic Band Chest Slaps - 2 sets x 15', '9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('c0d4682f-9e29-48d3-b9ab-fb107e334cb1', 0,
		'Start in a plank and walk feet up the wall. Brutal shoulder/core push.', 1, 'Wall Walk Push-Ups – 3 sets x 4',
		'9384e855-a088-4236-a10e-de5319740b59', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('8d124f12-797a-4929-8648-c23b418a6066', 0, 'Throw a towel over a closed door, pull yourself up like a DIY TRX.',
		0, 'Towel Door Rows - 4 sets x 10', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('17ebb06e-2bfa-494b-9aec-3aaece82521b', 0,
		'Climb up a soft incline or bench feet-first like a backwards monkey.', 1,
		'Reverse Crawl Ups (Feet First Crab Climb) – 3 x 10 seconds', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('7a841542-951b-4a29-b95b-3e86ac605bc8', 0, 'Lie face-down, lift arms and legs like you''re swimming.', 2,
		'Prone Swimmer Lifts – 3 sets x 15', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('bec4cb45-d9be-42f7-8482-0bb50b24769e', 0, 'Sit, slide heels in toward butt using hamstrings and biceps.', 3,
		'Sock Slide Curls (on smooth surface) – 3 x 12', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('25a78ed7-50ff-4771-8fe6-b0b6075bdfd7', 0, 'Pull rope through from a distance while seated or crawling.', 4,
		'Rope Pull-Throughs (Tug-of-War Style) – 3 x 20 seconds', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('b6e69a3d-97a2-43d9-a9ed-a385d042fe41', 0, 'Hang from a bar and shrug shoulders for weird lat work.', 5,
		'Dead Hang Shrugs – 3 sets x 10', '2f85821d-e8fc-4da2-8d6d-74af9e89b693', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('b0eae8fe-6258-4de9-816d-30ac40adc1b8', 0, 'Start in frog stand (arms inside knees), jump up.', 0,
		'Frog Stand to Jump Squats – 4 sets x 5', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('ffbc43ce-9ce7-429d-b043-3e8a14226e49', 0, 'Hold a broom for balance while doing pistol squats.', 1,
		'Broomstick Pistols (Counterbalance) – 3 x 6 per leg', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('a5e13765-f580-4acd-9cd6-13ad9a325b29', 0,
		'Duck walk forward, crawl back in reverse. Legs + coordination killer.', 2,
		'Duck Walk + Reverse Crawl Combo – 3 x 30 seconds', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('8ee3ea04-f18e-41a0-9260-69cdcadd9862', 0, 'Side lunge deep, reach opposite hand to foot.', 3,
		'Cossack Squats with Reach – 3 x 10', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('53b46ac1-c80c-4678-a4b1-6da4bb46ff58', 0, 'Hop side-to-side over a bag or box.', 4,
		'Step-Over Lateral Hops (over object) – 3 x 20 total', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('235913dd-988f-4615-a794-ff2e5600bccb', 0, 'Isometric wall sit with small calf raises.', 5,
		'Wall Sit with Ankle Pulses – 2 sets x 30 sec + 20 calf pulses', 'f3bc8876-18c2-4650-9c99-1dd2bd2b6589', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('6abe13a7-3714-4ed3-81a5-c1854ec68d82', 1, '', 0, 'Try pinch pots, coil pots, and slab building.',
		'754884c2-013c-47da-9fc5-05acd7bb7c76', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('51b40e9b-3745-44bd-85eb-b49101c7409e', 1, null, 1, 'Make a simple cup or bowl using hand techniques.',
		'754884c2-013c-47da-9fc5-05acd7bb7c76', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('9ba1ac4f-8f0e-4194-86a9-c3ecb4752221', 0, null, 0, 'Dedicate a table or corner in your home.',
		'2f683bd8-dddb-4548-9351-ff4b8301509e', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('29b4df34-34bf-4425-8036-ba8b378403c0', 1, '', 0, 'Read or watch an intro on TM.',
		'd264dc79-3dff-446f-85e6-8cbf61b5a773', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('932280ba-46a4-4f78-a1c1-2cdfff1d3ffc', 1, null, 1,
		'Learn about the use of a mantra and the “effortless” technique.', 'd264dc79-3dff-446f-85e6-8cbf61b5a773',
		null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('a937035e-7231-41a6-a56b-83ffe457ea82', 1, null, 0, 'Day 1', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('bf3d6975-85cb-4999-91ca-d8c4a8d5bfef', 0, null, 3, 'Day 4', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('514d021f-d304-49aa-8a5b-f45be61c70cb', 0, null, 4, 'Day 5', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('e87251bc-cb5e-4a1e-9759-7c45a1425e9a', 0, null, 5, 'Day 6', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('cfe9b8d0-10b2-4293-97b8-2006f64dc654', 0, null, 6, 'Day 7', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('75df9ca8-a145-4f57-8cfd-bfe0eb0e8fc8', 1, null, 1, 'Day 2', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('4560c84e-fd65-4b45-8d67-2970b110c768', 1, null, 2, 'Day 3', 'a2858f2b-455e-4e6c-8610-a796856907be', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('6b74e40b-f7a6-4537-a6ff-8c9887be54dd', 0, null, 0, 'Day 1', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('5885d9f5-3529-4ca7-b7f7-01453662d3a1', 0, null, 1, 'Day 2', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('93c1aba0-a0b9-4627-aff5-2661cf49f5bc', 0, null, 2, 'Day 3', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('1be24aff-d8e8-46b0-a3c2-587a5b5d2e8e', 0, null, 3, 'Day 4', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('0d21bdc0-b75a-4886-884c-d9c326fbec47', 0, null, 4, 'Day 5', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('c5a47ef7-1b58-407b-aaf0-83ca0aba55df', 0, null, 5, 'Day 6', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);
INSERT INTO public.step (id, completed_steps_count, description, index, title, parent_step_id, plan_id)
VALUES ('90aaf655-caa4-4978-a29f-cda42daa2153', 0, null, 6, 'Day 7', 'd1cfe84c-7e94-47f7-904a-7ea115fe1aad', null);

INSERT INTO public.plan_progress (id, acquired_at, comment, completed, last_active, last_synced_plan, plan_id, user_id)
VALUES ('e8f6d3bb-6103-4f96-bffa-db913852e785', '2025-07-06 13:03:33.928232', null, false, '2025-07-06 13:04:21.936351',
		'2025-07-06 12:39:51.276627', '809e0499-592a-4182-b869-016c4a960156', '96e286a4-4e8b-42d7-ae00-8555e20b455c');
INSERT INTO public.plan_progress (id, acquired_at, comment, completed, last_active, last_synced_plan, plan_id, user_id)
VALUES ('eff7d068-3d93-452c-92df-5ad65c4626d9', '2025-07-06 13:04:49.934141', null, false, '2025-07-06 13:07:19.523702',
		'2025-07-06 13:06:51.469623', '2abb24ec-afd3-42cf-a4b7-8daf0680cdf6', '404947c7-47df-47da-a635-dafe2da76f50');

INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('01fe955e-3829-4b91-ac88-b440c92af4bd', null, false, null, 'e8f6d3bb-6103-4f96-bffa-db913852e785',
		'2f683bd8-dddb-4548-9351-ff4b8301509e');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('03085264-9bd4-416c-90d5-f8e3b8bcefda', null, true, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'5ccce5fd-bef4-4c44-9eb5-1f2c15fb21ba');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('1c505f10-66ac-4d90-9358-10bf65c3121b', null, true, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'57b9d76d-640e-4efd-b451-dea105248f11');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('90566258-3c7b-4ea0-a672-5c754da28e34', null, false, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'd1cfe84c-7e94-47f7-904a-7ea115fe1aad');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('1d20a4c1-fb23-40d4-861f-e04bdeac04f5', null, false, null, 'e8f6d3bb-6103-4f96-bffa-db913852e785',
		'95eb7403-40c2-4cf1-ae4c-d1916b558ab0');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('88874139-5a16-4dfe-8e99-39371cf3efc2', null, false, null, 'e8f6d3bb-6103-4f96-bffa-db913852e785',
		'22bb0cb9-ad8c-4064-94b3-59fceb574beb');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('e4cfcbf2-7287-4649-91d2-ad550b222c58', null, true, null, 'e8f6d3bb-6103-4f96-bffa-db913852e785',
		'967f7c6e-9fd8-42df-aab0-5d460fa315b2');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('ba0b6a52-c924-4c6c-8ea3-70a5f4a4c191', null, false, null, 'e8f6d3bb-6103-4f96-bffa-db913852e785',
		'754884c2-013c-47da-9fc5-05acd7bb7c76');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('1787957b-30bf-41a1-8d7a-03f978721235', null, false, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'a2858f2b-455e-4e6c-8610-a796856907be');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('0eedca2a-b202-441a-9921-12125fbd26ee', null, false, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'2a9b318f-d6a2-453d-b534-2f10d49a93ee');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('f3dd4458-9a92-448d-9c9a-bb5335a479c6', null, true, null, 'eff7d068-3d93-452c-92df-5ad65c4626d9',
		'd264dc79-3dff-446f-85e6-8cbf61b5a773');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('16240952-872b-47cd-8e42-1283d117a81d', null, false, '1d20a4c1-fb23-40d4-861f-e04bdeac04f5', null,
		'b46c5fb7-6a81-4461-9056-b5c370cc31b5');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('e516c96f-78ca-4480-b858-ff95885b54f5', null, false, '88874139-5a16-4dfe-8e99-39371cf3efc2', null,
		'c4cc4ada-7784-4641-8140-6201cdcb840b');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('4a5f0a2b-747c-4d43-b77d-fe6c5b8e6a92', null, false, '88874139-5a16-4dfe-8e99-39371cf3efc2', null,
		'7216cff2-5669-419e-821e-f0989200b290');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('d4fc3aa7-b641-4fab-b6e2-e215bdb7ab4e', null, false, '01fe955e-3829-4b91-ac88-b440c92af4bd', null,
		'23cb3a7e-1be8-44ec-8141-22cdbd969deb');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('f0b8e829-0b58-415a-b233-68ff9a0daa52', null, true, '1d20a4c1-fb23-40d4-861f-e04bdeac04f5', null,
		'e51afd0e-b1f7-4015-aeac-a9b8111714f5');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('e41be558-5f2e-47e2-a6ec-23f4d847bb6c', null, false, 'ba0b6a52-c924-4c6c-8ea3-70a5f4a4c191', null,
		'51b40e9b-3745-44bd-85eb-b49101c7409e');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('8c60f3f4-e0ef-4640-bcbe-3ffe4856bfce', null, false, 'ba0b6a52-c924-4c6c-8ea3-70a5f4a4c191', null,
		'6abe13a7-3714-4ed3-81a5-c1854ec68d82');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('2939d2ba-eefe-4f2b-a5b2-045e82a8062a', null, true, '01fe955e-3829-4b91-ac88-b440c92af4bd', null,
		'9ba1ac4f-8f0e-4194-86a9-c3ecb4752221');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('f580d20b-659b-43f8-bd72-e832d017b990', null, true, 'f3dd4458-9a92-448d-9c9a-bb5335a479c6', null,
		'29b4df34-34bf-4425-8036-ba8b378403c0');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('36b89a67-b40f-45e8-a5c0-c49006864038', null, true, 'f3dd4458-9a92-448d-9c9a-bb5335a479c6', null,
		'932280ba-46a4-4f78-a1c1-2cdfff1d3ffc');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('b4f32c83-6920-4375-a5fe-b30a978b1676', null, false, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'bf3d6975-85cb-4999-91ca-d8c4a8d5bfef');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('13d53195-2f11-4cb0-96c5-43546f9f12b7', null, false, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'514d021f-d304-49aa-8a5b-f45be61c70cb');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('963d12e7-6f09-40fb-9679-295f918d5370', null, false, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'e87251bc-cb5e-4a1e-9759-7c45a1425e9a');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('60afc5fb-e964-45f2-b629-12d3b6c39b9a', null, false, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'cfe9b8d0-10b2-4293-97b8-2006f64dc654');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('ab5ef3e1-4606-4f33-8338-85c1bf8f19c2', null, true, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'a937035e-7231-41a6-a56b-83ffe457ea82');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('7b04aa2c-b708-432c-8349-a7df0f03525c', null, true, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'75df9ca8-a145-4f57-8cfd-bfe0eb0e8fc8');
INSERT INTO public.step_progress (id, comment, completed, parent_step_id, plan_id, step_id)
VALUES ('1827d893-c9ca-47c2-a62b-a9b57058c8a0', null, true, '1787957b-30bf-41a1-8d7a-03f978721235', null,
		'4560c84e-fd65-4b45-8d67-2970b110c768');

INSERT INTO public.plan_invite (id, created_at, responded_at, status, plan_id, recipient_id, sender_id)
VALUES ('27bb85f1-58b1-4995-b145-593604f45401', '2025-07-06 13:10:36.526590', null, 'PENDING',
		'434767a0-314d-49d5-8d46-ee46ba96d186', '96e286a4-4e8b-42d7-ae00-8555e20b455c',
		'404947c7-47df-47da-a635-dafe2da76f50');
